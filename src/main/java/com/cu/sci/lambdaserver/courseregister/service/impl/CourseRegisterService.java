package com.cu.sci.lambdaserver.courseregister.service.impl;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.courseclass.service.ICourseClassTimingService;
import com.cu.sci.lambdaserver.courseregister.entities.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.entities.CourseRegisterLog;
import com.cu.sci.lambdaserver.courseregister.entities.CourseRegisterSession;
import com.cu.sci.lambdaserver.courseregister.repositories.CourseRegisterLogRepository;
import com.cu.sci.lambdaserver.courseregister.repositories.CourseRegisterRepository;
import com.cu.sci.lambdaserver.courseregister.repositories.CourseRegisterSessionRepository;
import com.cu.sci.lambdaserver.courseregister.repositories.CourseResultRepository;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedAccessService;
import com.cu.sci.lambdaserver.courseregister.service.ICourseClassSession;
import com.cu.sci.lambdaserver.courseregister.service.ICourseRegisterService;
import com.cu.sci.lambdaserver.courseregister.service.ICourseRegistrationLogService;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.CourseClassState;
import com.cu.sci.lambdaserver.utils.enums.CourseResultState;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseRegisterService implements ICourseRegisterService {
    private final CourseRegisterLogRepository courseRegisterLogRepository;

    private final ICourseClassSession courseClassSession;
    private final ICourseClassTimingService courseClassTimingService;
    private final IAuthenticatedAccessService authenticatedAccessService;
    private final ICourseRegistrationLogService courseRegistrationLogService;

    private final IMapper<Course, CourseDto> courseMapper;
    private final CourseClassMapper courseClassMapper;

    private final CourseRepository courseRepository;
    private final CourseRegisterRepository courseRegisterRepository;
    private final StudentRepository studentRepository;
    private final CourseResultRepository courseResultRepository;
    private final CourseRegisterSessionRepository courseRegisterSessionRepository;
    private final CourseClassRepository courseClassRepository;

    @Override
    public Set<CourseDto> getStudentAvailableCourses(String studentUsername) {
        authenticatedAccessService.checkAccessRegisterOrResultResource(studentUsername);

        Student student = studentRepository.findByUsernameIgnoreCase(studentUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));
        String departmentCode = student.getDepartment().getCode();


        Set<String> passed =
                courseResultRepository.findByStudentAndState(student, CourseResultState.PASSED)
                        .stream().map(
                                courseResult ->
                                        courseResult
                                                .getCourseClass()
                                                .getCourse().getCode().toUpperCase()
                        ).collect(Collectors.toSet());


        Set<Course> notPassed = passed.isEmpty()
                ? courseRepository.findByDepartmentsDepartmentCodeIgnoreCase(departmentCode)
                : courseRepository
                .findByDepartmentsDepartmentCodeAndCodeNotInAllIgnoreCase(
                        departmentCode,
                        passed
                );

        Set<Course> availableInSystem = notPassed.stream().filter(
                c -> courseClassRepository.countByCourseAndState(c, CourseClassState.REGISTRATION) > 0
        ).collect(Collectors.toSet());

        Set<Course> canRegister = availableInSystem.stream().filter(
                notPassedCourse -> canRegister(
                        passed,
                        notPassedCourse
                                .getCoursePrerequisites()
                                .stream().map(Course::getCode)
                                .collect(Collectors.toSet())
                )).collect(Collectors.toSet());


        return canRegister.stream().map(courseMapper::mapTo).collect(Collectors.toSet());
    }

    @Override
    public MessageResponse takeASeatAtCourseClass(String studentUsername, String course, Integer years, YearSemester semester, Integer groupNumber) {
        authenticatedAccessService.checkAccessRegisterOrResultResource(studentUsername);

        Student student = studentRepository.findByUsernameIgnoreCase(studentUsername)
                .orElseThrow((() -> new ResponseStatusException(HttpStatus.CONFLICT, "Student Not Found!")));

        CourseClass courseClass = courseClassRepository.findBySemesterIsAndYearIsAndCourseCodeIsAndGroupNumberIsAllIgnoreCase(semester, years, course, groupNumber)
                .orElseThrow((() -> new ResponseStatusException(HttpStatus.CONFLICT, "Course Class Not Found!")));

        if (courseClassSession.hasSession(student, courseClass)) {
            return new MessageResponse("You already has session");
        }

        if (!canRegister(student, courseClass)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Can't register this course");
        }
        if (isCourseClassCollision(student, courseClass)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You Can't register this course because having Collision");
        }


        int totalRegistered =
                courseClass.getNumberOfStudentsRegistered() +
                        courseClassSession.getActiveSessions(courseClass);

        if (totalRegistered >= courseClass.getMaxCapacity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Class is full");
        }

        courseRegisterSessionRepository.save(
                CourseRegisterSession.builder()
                        .student(student)
                        .courseClass(courseClass)
                        .build()
        );

        return new MessageResponse("A Seat taken successfully");
    }

    @Override
    public MessageResponse registerCourseClass(String studentUsername, String course, Integer years, YearSemester semester, Integer groupNumber) {
        authenticatedAccessService.checkAccessRegisterOrResultResource(studentUsername);

        Student student = studentRepository.findByUsernameIgnoreCase(studentUsername)
                .orElseThrow((() -> new ResponseStatusException(HttpStatus.CONFLICT, "Student Not Found!")));

        CourseClass courseClass = courseClassRepository.findBySemesterIsAndYearIsAndCourseCodeIsAndGroupNumberIsAllIgnoreCase(semester, years, course, groupNumber)
                .orElseThrow((() -> new ResponseStatusException(HttpStatus.CONFLICT, "Course Class Not Found!")));

        /*
        1. check course is available to me (canRegister)
        2. course isn't registered
        3. check intersection
        4. check if session or capacity

        Register course class and log
         */



        /* Checkers */
        if (!canRegister(student, courseClass)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Can't register this course");
        }
        if (courseRegisterRepository
                .existsByStudentAndCourseClassCourse(
                        student, courseClass.getCourse()
                )) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "course is already registered");
        }

        if (isCourseClassCollision(student, courseClass)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You Can't register this course because having Collision");
        }


        if (!courseClassSession.hasSession(student, courseClass)) {
            int totalRegistered =
                    courseClass.getNumberOfStudentsRegistered() +
                            courseClassSession.getActiveSessions(courseClass);

            if (totalRegistered >= courseClass.getMaxCapacity()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Class is full");
            }
        }


        /* Business */
        courseRegisterRepository.save(CourseRegister.builder()
                .courseClass(courseClass)
                .student(student)
                .build());

        courseClass.setNumberOfStudentsRegistered(courseClass.getNumberOfStudentsRegistered() + 1);
        student.setCreditHoursSemester(
                student.getCreditHoursSemester()
                        + courseClass.getCourse().getCreditHours()
        );

        courseClassRepository.save(courseClass);
        studentRepository.save(student);


        // registration logs
        courseRegistrationLogService.log(
                CourseRegisterLog.Action.ADD,
                student,
                courseClass
        );

        return new MessageResponse("Course Registered Successfully");
    }

    @Override
    public MessageResponse removeCourseClass(String studentUsername, String course, Integer years, YearSemester semester, Integer groupNumber) {
        authenticatedAccessService.checkAccessRegisterOrResultResource(studentUsername);

        Student student = studentRepository.findByUsernameIgnoreCase(studentUsername)
                .orElseThrow((() -> new ResponseStatusException(HttpStatus.CONFLICT, "Student Not Found!")));

        CourseClass courseClass = courseClassRepository.findBySemesterIsAndYearIsAndCourseCodeIsAndGroupNumberIsAllIgnoreCase(semester, years, course, groupNumber)
                .orElseThrow((() -> new ResponseStatusException(HttpStatus.CONFLICT, "Course Class Not Found!")));

        CourseRegister register = courseRegisterRepository.findByStudentAndCourseClass(student, courseClass)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Course isn't registered"));

        courseClass.setNumberOfStudentsRegistered(courseClass.getNumberOfStudentsRegistered() - 1);
        student.setCreditHoursSemester(
                student.getCreditHoursSemester()
                        - courseClass.getCourse().getCreditHours()
        );

        courseClassRepository.save(courseClass);
        studentRepository.save(student);
        courseRegisterRepository.delete(register);


        // registration logs
        courseRegistrationLogService.log(
                CourseRegisterLog.Action.REMOVE,
                student,
                courseClass
        );

        return new MessageResponse("Course removed Successfully");
    }

    @Override
    public Set<CourseClassDto> getRegisteredCourseClasses(String studentUsername) {
        authenticatedAccessService.checkAccessRegisterOrResultResource(studentUsername);

        Student student = studentRepository.findByUsernameIgnoreCase(studentUsername)
                .orElseThrow((() -> new ResponseStatusException(HttpStatus.CONFLICT, "Student Not Found!")));

        Set<CourseRegister> registers = courseRegisterRepository.findAllByStudent(student);

        return registers.stream().map(reg ->
                        courseClassMapper.mapTo(reg.getCourseClass(),
                                Set.of(CourseClassMapper.Include.TIMINGS,
                                        CourseClassMapper.Include.COURSE
                                )))
                .collect(Collectors.toSet());
    }


    @Override
    public boolean canRegister(Set<String> passed, Set<String> prerequisites) {
        boolean can = true;
        for (String prerequisite : prerequisites) {
            can &= passed.contains(prerequisite);
        }
        return can;
    }

    @Override
    public boolean canRegister(Set<Course> passed, Course course) {
        return canRegister(
                passed.stream().map(Course::getCode)
                        .collect(Collectors.toSet()),
                course
                        .getCoursePrerequisites()
                        .stream().map(Course::getCode)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public boolean canRegister(Student student, CourseClass courseClass) {
        Course course = courseClass.getCourse();

        return course.getDepartments().stream().map(
                        d -> d.getDepartment().getCode().toUpperCase()
                ).collect(Collectors.toSet())
                .contains(student.getDepartment().getCode().toUpperCase())
                && canRegister(
                courseResultRepository.findByStudentAndState(student, CourseResultState.PASSED)
                        .stream().map(
                                courseResult -> courseResult.getCourseClass().getCourse()
                        ).collect(Collectors.toSet()),
                course
        );

    }

    @Override
    public boolean isCourseClassCollision(Student student, CourseClass courseClass) {
        boolean intersect = false;
        Set<CourseClassTiming> timings = courseClass.getTimings();
        Set<CourseClass> registered = courseRegisterRepository.findAllByStudent(student).stream().map(CourseRegister::getCourseClass).collect(Collectors.toSet());

        for (CourseClass regClas : registered) {
            for (CourseClassTiming regTime : regClas.getTimings()) {
                for (CourseClassTiming time : timings) {
                    intersect |= courseClassTimingService.isIntersect(time, regTime);
                }
            }
        }

        return intersect;
    }


    /*-----------------------------------------------------------
            private Methods
     ----------------------------------------------------------- **/


//    void confirmCourseRegister(String studentCode) {
//        //get course register
//        Collection<CourseRegister> courseRegisters = courseRegisterRepository.findAllByStudent_CodeAndState(studentCode, CourseRegisterState.REGISTERING);
//
//        // check if collection empty
//        if (courseRegisters.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "No course registrations not confirmed for student with code: " + studentCode);
//        }
//
//        //update state
//        courseRegisters.stream().forEach(courseRegister -> {
//            if (courseRegister.getCourseClass().getYear() == Year.now().getValue()) {
//                courseRegister.setState(CourseRegisterState.STUDYING);
//            }
//
//            courseRegisterRepository.save(courseRegister);
//        });
//    }
//
//    @Override
//    public void assignGradeToCourseRegister(Long grade, CourseRegister courseRegister) {
//
//        if(grade<GradeBounds.UPPER_BOUND_FAIL.getValue()){
//            courseRegister.setRate(Rate.FAIL);
//        } else if (grade<GradeBounds.UPPER_BOUND_POOR.getValue()) {
//            courseRegister.setRate(Rate.POOR);
//        } else if (grade<GradeBounds.UPPER_BOUND_GOOD.getValue()) {
//            courseRegister.setRate(Rate.GOOD);
//        } else if (grade<GradeBounds.UPPER_BOUND_VERY_GOOD.getValue()) {
//            courseRegister.setRate(Rate.VERY_GOOD);
//        }else{
//            courseRegister.setRate(Rate.EXCELLENT);
//        }
//
//    }
//
//
//    @Override
//    public CourseRegisterOutDto createCourseRegister(CourseRegisterInDto courseRegisterInDto) {
//
//        //get user
//        User user = iAuthenticationFacade.getAuthenticatedUser();
//        Student student = null ;
//
//        //check if user is student
//        if(user.getRoles().contains(Role.STUDENT)){
//            student = studentRepository.findById(user.getId())
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));
//        }
//        //check if user is admin or academic advisor
//        else if(user.getRoles().contains(Role.ADMIN)|| user.getRoles().contains(Role.ACADEMIC_ADVISOR)){
//            student = studentRepository.findByCode(courseRegisterInDto.getStudentCode())
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));
//        }
//
//        Course course = courseRepository.findByCodeIgnoreCase(courseRegisterInDto.getCourseCode())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code "));
//
//        Optional<CourseRegister> courseRegisterWithSameCourse = courseRegisterRepository.findByCourseClassCourseAndState(course, CourseRegisterState.REGISTERING);
//        if (courseRegisterWithSameCourse.isPresent()) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Register With The Same Course already exists");
//        }
//
//        //get course class
//        CourseClass courseClass = courseClassRepository.findByCourseIdAndGroupNumber(course.getId(), courseRegisterInDto.getGroupNumber())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this group number "));
//
//
//        //check course class state
//        if (!courseClass.getState().equals(CourseClassState.IN_PROGRESS)) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Class is not active");
//        }
//
//        if (!(courseClass.getYear() == Year.now().getValue())) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Class is not available for this year");
//        }
//
//        if(courseClass.getNumberOfStudentsRegistered().equals(courseClass.getMaxCapacity())){
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Class is full");
//        }
//
//        //create course register
//        CourseRegister newCourseRegister = CourseRegister
//                .builder()
//                .courseClass(courseClass)
//                .student(student)
//                .state(CourseRegisterState.REGISTERING)
//                .build();
//
//        //save course register
//        CourseRegister savedCourseRegister = courseRegisterRepository.save(newCourseRegister);
//
//        courseClass.setNumberOfStudentsRegistered(courseClass.getNumberOfStudentsRegistered() + 1);
//        courseClassRepository.save(courseClass);
//
//        //update student credit hours
//        student.setCreditHoursSemester(student.getCreditHoursSemester() + courseClass.getCourse().getCreditHours());
//        studentRepository.save(student);
//
//
//        //return course register
//        return courseRegisterResponseDtoMapper.mapTo(savedCourseRegister);
//
//    }
//
//    @Override
//    public MessageResponse adminConfirmCourseRegister(String studentCode) {
//        //check student code
//        studentRepository.findByCode(studentCode)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code " + studentCode));
//
//        // Confirm CourseRegister
//        confirmCourseRegister(studentCode);
//
//        return new MessageResponse("Course Register Confirmed Successfully");
//    }
//
//
//    @Override
//    public MessageResponse studentConfirmCourseRegister() {
//        //get Authenticated Student
//        User user = iAuthenticationFacade.getAuthenticatedUser();
//
//        //get course register
//        confirmCourseRegister(user.getUsername());
//
//        return new MessageResponse("Course Register Confirmed Successfully");
//    }
//
//
//    @Override
//    public Collection<CourseRegisterOutDto> studentGetAllCourseRegisters() {
//        User user = iAuthenticationFacade.getAuthenticatedUser();
//        return courseRegisterRepository.findAllByStudentId(user.getId()).stream()
//                .map(courseRegisterOutDtoMapper::mapTo).collect(Collectors.toList());
//    }
//
//
//    @Override
//    public MessageResponse addGrade(String studentCode, Long courseClassId, Long grade) {
//
//        //check student code
//        studentRepository.findByCode(studentCode)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code " + studentCode));
//
//        //get course register
//        Optional<CourseRegister> courseRegisterOptional = courseRegisterRepository
//                .findByCourseClass_IdAndStudent_CodeAndState(courseClassId, studentCode, CourseRegisterState.STUDYING);
//
//        if (courseRegisterOptional.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No course register found for student with code: " + studentCode);
//        }
//
//        CourseRegister courseRegister = courseRegisterOptional.get();
//
//        //check if grade is valid
//        if (grade < 0 || grade > 100) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "grade must be between 0 and 100");
//        }
//
//        //update course register state and grade
//        if(grade>=60){
//            courseRegister.setState(CourseRegisterState.PASSED);
//        }else {
//            courseRegister.setState(CourseRegisterState.FAILED);
//            courseRegister.setNumberOfFailed(courseRegister.getNumberOfFailed() + 1);
//        }
//
//        courseRegister.setGrade(grade);
//        assignGradeToCourseRegister(grade, courseRegister);
//        courseRegisterRepository.save(courseRegister);
//
//        return new MessageResponse("Grade added successfully");
//    }
//
//
//    @Override
//    public CourseRegisterOutDto getCourseRegister(Long id) {
//        return courseRegisterRepository.findById(id)
//                .map(courseRegisterOutDtoMapper::mapTo)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found with this id"));
//    }
//
//
//    @Override
//    public CourseRegisterOutDto updateCourseRegister(Long courseClassId, Long courseRegisterId) {
//
//
//        CourseClass courseClass = courseClassRepository.findById(courseClassId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this Id " +courseClassId));
//
//        Optional<CourseRegister> courseRegisterOptional = courseRegisterRepository.findByCourseClassCourseAndState(courseClass.getCourse(), CourseRegisterState.REGISTERING);
//        if (courseRegisterOptional.isPresent()) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Register With The Same Course already exists");
//        }
//
//        Optional<CourseRegister> courseRegister = courseRegisterRepository.findById(courseRegisterId);
//        if (courseRegister.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found ");
//        }
//        if(!(courseRegister.get().getState().equals(CourseRegisterState.REGISTERING))){
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Register is already confirmed");
//        }
//
//        courseRegister.get().setCourseClass(courseClass);
//
//        courseRegisterRepository.save(courseRegister.get());
//
//        return courseRegisterOutDtoMapper.mapTo(courseRegister.get());
//
//    }
//
//
//    @Override
//    @Transactional
//    public MessageResponse deleteCourseRegister(Long courseClassId) {
//
//        User user = iAuthenticationFacade.getAuthenticatedUser() ;
//
//        Optional<Student> student = studentRepository.findByCode(user.getUsername()) ;
//        if(student.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found ");
//        }
//
//        Optional<CourseClass>courseClass = courseClassRepository.findById(courseClassId) ;
//        if(courseClass.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found ");
//        }
//
//        Optional<CourseRegister> courseRegister = courseRegisterRepository
//                .findByCourseClass_IdAndStudent_CodeAndState(courseClassId, user.getUsername(), CourseRegisterState.REGISTERING);
//
//        if(courseRegister.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found ");
//        }
//
//        courseRegisterRepository.delete(courseRegister.get());
//
//        courseClass.get().setNumberOfStudentsRegistered(courseClass.get().getNumberOfStudentsRegistered()-1);
//
//        courseClassRepository.save(courseClass.get());
//
//        student.get().setCreditHoursSemester((student.get().getCreditHoursSemester())-(courseClass.get().getCourse().getCreditHours()));
//        studentRepository.save(student.get());
//
//        return new MessageResponse("Course Register Deleted Successfully");
//    }
//
//    @Override
//    public MessageResponse deleteCourseRegisterByStudentCode(String studentCode, Long courseClassId) {
//
//        Student student = studentRepository.findByCode(studentCode)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code " + studentCode));
//
//        CourseClass courseClass = courseClassRepository.findById(courseClassId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this Id " + courseClassId));
//
//        CourseRegister courseRegister = courseRegisterRepository
//                .findByCourseClass_IdAndStudent_CodeAndState(courseClassId, studentCode, CourseRegisterState.REGISTERING)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found "));
//
//        courseRegisterRepository.delete(courseRegister);
//
//        courseClass.setNumberOfStudentsRegistered(courseClass.getNumberOfStudentsRegistered() - 1);
//
//        courseClassRepository.save(courseClass);
//
//        student.setCreditHoursSemester((student.getCreditHoursSemester()) - (courseClass.getCourse().getCreditHours()));
//        studentRepository.save(student);
//
//        return new MessageResponse("Course Register Deleted Successfully");
//
//
//    }
//
//
//    @Override
//    public Collection<StudentDto> getAllCourseClassStudents(Long courseClassId) {
//        Collection<CourseRegister> courseRegisters = courseRegisterRepository
//                .findAllByCourseClass_Id(courseClassId);
//        Collection<Student> courseClassStudents = courseRegisters.stream()
//                .map(CourseRegister::getStudent).collect(Collectors.toList());
//        return courseClassStudents.stream().map(studentDtoIMapper::mapTo).collect(Collectors.toList());
//    }
//
//    @Override
//    public Collection<CourseRegisterOutDto> getMyReslut(YearSemester semester, Integer year) {
//        User user = iAuthenticationFacade.getAuthenticatedUser();
//
//        List<CourseRegisterOutDto> courseRegisterOutDtos = courseRegisterRepository
//                .findByCourseClassYearAndCourseClassSemesterAndStudentIdAndStateIn(year, semester, user.getId(), List.of(CourseRegisterState.PASSED, CourseRegisterState.FAILED))
//                .stream().map(courseRegisterOutDtoMapper::mapTo).toList();
//
//        if (courseRegisterOutDtos.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No course registrations found for student with code: " + user.getUsername());
//        }
//        System.out.println(courseRegisterOutDtos);
//        return courseRegisterOutDtos;
//
//    }
//
//
//    @Override
//    public Collection<CourseRegisterOutDto> getCourseRegistersByState(String studentCode, CourseRegisterState state) {
//        //check if student exists
//        studentRepository.findByCode(studentCode)
//                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));
//
//        //return course registers by state
//        Collection<CourseRegister> courseRegisters = courseRegisterRepository.findAllByStudent_CodeAndState(studentCode, state);
//        if (courseRegisters.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "No course registrations found for student with code: " + studentCode);
//        }
//        return courseRegisters
//                .stream()
//                .map(courseRegisterResponseDtoMapper::mapTo)
//                .toList();
//    }
//
//
//    @Override
//    public Collection<CourseRegisterOutDto> getStudentRegisteredCourses(String studentCode) {
//        Student student = studentRepository.findByCode(studentCode)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));
//        return courseRegisterRepository.findAllByStudentId(student.getId())
//                .stream().map(courseRegisterOutDtoMapper::mapTo).collect(Collectors.toList());
//    }

}
