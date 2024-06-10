package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.CourseRegisterRepository;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.courseregister.mapper.CourseRegisterOutDtoMapper;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.*;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseRegisterService implements ICourseRegisterService {

    private final CourseRegisterRepository courseRegisterRepository;
    private final StudentRepository studentRepository;
    private final CourseClassRepository courseClassRepository;
    private final CourseRepository courseRepository;
    private final CourseRegisterOutDtoMapper courseRegisterResponseDtoMapper;
    private final CourseRegisterOutDtoMapper courseRegisterOutDtoMapper;
    private final IMapper<Student, StudentDto> studentDtoIMapper;
    private final IAuthenticationFacade iAuthenticationFacade;


    void confirmCourseRegister(String studentCode) {
        //get course register
        Collection<CourseRegister> courseRegisters = courseRegisterRepository.findAllByStudent_CodeAndState(studentCode, CourseRegisterState.REGISTERING);

        // check if collection empty
        if (courseRegisters.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No course registrations not confirmed for student with code: " + studentCode);
        }

        //update state
        courseRegisters.stream().forEach(courseRegister -> {
            if (courseRegister.getCourseClass().getYear().equals(Year.now().toString())) {
                courseRegister.setState(CourseRegisterState.STUDYING);
            }
            courseRegisterRepository.save(courseRegister);
        });
    }

    @Override
    public void assignGradeToCourseRegister(Long grade, CourseRegister courseRegister) {

        if(grade<GradeBounds.UPPER_BOUND_FAIL.getValue()){
            courseRegister.setRate(Rate.FAIL);
        } else if (grade<GradeBounds.UPPER_BOUND_POOR.getValue()) {
            courseRegister.setRate(Rate.POOR);
        } else if (grade<GradeBounds.UPPER_BOUND_GOOD.getValue()) {
            courseRegister.setRate(Rate.GOOD);
        } else if (grade<GradeBounds.UPPER_BOUND_VERY_GOOD.getValue()) {
            courseRegister.setRate(Rate.VERY_GOOD);
        }else{
            courseRegister.setRate(Rate.EXCELLENT);
        }

    }


    @Override
    public CourseRegisterOutDto createCourseRegister(CourseRegisterInDto courseRegisterInDto) {

        //get user
        User user = iAuthenticationFacade.getAuthenticatedUser();
        Student student = null ;

        //check if user is student
        if(user.getRoles().contains(Role.STUDENT)){
            student = studentRepository.findById(user.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));
        }
        //check if user is admin or academic advisor
        else if(user.getRoles().contains(Role.ADMIN)|| user.getRoles().contains(Role.ACADEMIC_ADVISOR)){
            student = studentRepository.findByCode(courseRegisterInDto.getStudentCode())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));
        }

        Course course = courseRepository.findByCodeIgnoreCase(courseRegisterInDto.getCourseCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code "));

        Optional<CourseRegister> courseRegisterWithSameCourse = courseRegisterRepository.findByCourseClassCourseAndState(course, CourseRegisterState.REGISTERING);
        if (courseRegisterWithSameCourse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Register With The Same Course already exists");
        }

        //get course class
        CourseClass courseClass = courseClassRepository.findByCourseIdAndGroupNumber(course.getId(), courseRegisterInDto.getGroupNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this group number "));


        //check course class state
        if (!courseClass.getCourseState().equals(State.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Class is not active");
        }

        if (!(courseClass.getYear().equals(Year.now().toString()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Class is not available for this year");
        }

        if(courseClass.getNumberOfStudentsRegistered().equals(courseClass.getMaxCapacity())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Class is full");
        }

        //create course register
        CourseRegister newCourseRegister = CourseRegister
                .builder()
                .courseClass(courseClass)
                .student(student)
                .state(CourseRegisterState.REGISTERING)
                .build();

        //save course register
        CourseRegister savedCourseRegister = courseRegisterRepository.save(newCourseRegister);

        courseClass.setNumberOfStudentsRegistered(courseClass.getNumberOfStudentsRegistered() + 1);
        courseClassRepository.save(courseClass);

        //update student credit hours
        student.setCreditHoursSemester(student.getCreditHoursSemester() + courseClass.getCourse().getCreditHours());
        studentRepository.save(student);


        //return course register
        return courseRegisterResponseDtoMapper.mapTo(savedCourseRegister);

    }

    @Override
    public MessageResponse adminConfirmCourseRegister(String studentCode) {
        //check student code
        studentRepository.findByCode(studentCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code " + studentCode));

        // Confirm CourseRegister
        confirmCourseRegister(studentCode);

        return new MessageResponse("Course Register Confirmed Successfully");
    }


    @Override
    public MessageResponse studentConfirmCourseRegister() {
        //get Authenticated Student
        User user = iAuthenticationFacade.getAuthenticatedUser();

        //get course register
        confirmCourseRegister(user.getUsername());

        return new MessageResponse("Course Register Confirmed Successfully");
    }


    @Override
    public Collection<CourseRegisterOutDto> studentGetAllCourseRegisters() {
        User user = iAuthenticationFacade.getAuthenticatedUser();
        return courseRegisterRepository.findAllByStudentId(user.getId()).stream()
                .map(courseRegisterOutDtoMapper::mapTo).collect(Collectors.toList());
    }


    @Override
    public MessageResponse addGrade(String studentCode, Long courseClassId, Long grade) {

        //check student code
        studentRepository.findByCode(studentCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code " + studentCode));

        //get course register
        Optional<CourseRegister> courseRegisterOptional = courseRegisterRepository
                .findByCourseClass_CourseClassIdAndStudent_CodeAndState(courseClassId, studentCode, CourseRegisterState.STUDYING);

        if (courseRegisterOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No course register found for student with code: " + studentCode);
        }

        CourseRegister courseRegister = courseRegisterOptional.get();

        //check if grade is valid
        if (grade < 0 || grade > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "grade must be between 0 and 100");
        }

        //update course register state and grade
        if(grade>=60){
            courseRegister.setState(CourseRegisterState.PASSED);
        }else {
            courseRegister.setState(CourseRegisterState.FAILED);
            courseRegister.setNumberOfFailed(courseRegister.getNumberOfFailed() + 1);
        }

        courseRegister.setGrade(grade);
        assignGradeToCourseRegister(grade, courseRegister);
        courseRegisterRepository.save(courseRegister);

        return new MessageResponse("Grade added successfully");
    }


    @Override
    public CourseRegisterOutDto getCourseRegister(Long id) {
        return courseRegisterRepository.findById(id)
                .map(courseRegisterOutDtoMapper::mapTo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found with this id"));
    }


    @Override
    public CourseRegisterOutDto updateCourseRegister(Long courseClassId, Long courseRegisterId) {


        CourseClass courseClass = courseClassRepository.findById(courseClassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this Id " +courseClassId));

        Optional<CourseRegister> courseRegisterOptional = courseRegisterRepository.findByCourseClassCourseAndState(courseClass.getCourse(), CourseRegisterState.REGISTERING);
        if (courseRegisterOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Register With The Same Course already exists");
        }

        Optional<CourseRegister> courseRegister = courseRegisterRepository.findById(courseRegisterId);
        if (courseRegister.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found ");
        }
        if(!(courseRegister.get().getState().equals(CourseRegisterState.REGISTERING))){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Register is already confirmed");
        }

        courseRegister.get().setCourseClass(courseClass);

        courseRegisterRepository.save(courseRegister.get());

        return courseRegisterOutDtoMapper.mapTo(courseRegister.get());

    }


    @Override
    @Transactional
    public MessageResponse deleteCourseRegister(Long courseClassId) {

        User user = iAuthenticationFacade.getAuthenticatedUser() ;

        Optional<Student> student = studentRepository.findByCode(user.getUsername()) ;
        if(student.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found ");
        }

        Optional<CourseClass>courseClass = courseClassRepository.findById(courseClassId) ;
        if(courseClass.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found ");
        }

        Optional<CourseRegister> courseRegister = courseRegisterRepository
                .findByCourseClass_CourseClassIdAndStudent_CodeAndState(courseClassId,user.getUsername(),CourseRegisterState.REGISTERING) ;

        if(courseRegister.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found ");
        }

        courseRegisterRepository.delete(courseRegister.get());

        courseClass.get().setNumberOfStudentsRegistered(courseClass.get().getNumberOfStudentsRegistered()-1);

        courseClassRepository.save(courseClass.get());

        student.get().setCreditHoursSemester((student.get().getCreditHoursSemester())-(courseClass.get().getCourse().getCreditHours()));
        studentRepository.save(student.get());

        return new MessageResponse("Course Register Deleted Successfully");
    }

    @Override
    public MessageResponse deleteCourseRegisterByStudentCode(String studentCode, Long courseClassId) {

        Student student = studentRepository.findByCode(studentCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code " + studentCode));

        CourseClass courseClass = courseClassRepository.findById(courseClassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this Id " + courseClassId));

        CourseRegister courseRegister = courseRegisterRepository
                .findByCourseClass_CourseClassIdAndStudent_CodeAndState(courseClassId, studentCode, CourseRegisterState.REGISTERING)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found "));

        courseRegisterRepository.delete(courseRegister);

        courseClass.setNumberOfStudentsRegistered(courseClass.getNumberOfStudentsRegistered() - 1);

        courseClassRepository.save(courseClass);

        student.setCreditHoursSemester((student.getCreditHoursSemester()) - (courseClass.getCourse().getCreditHours()));
        studentRepository.save(student);

        return new MessageResponse("Course Register Deleted Successfully");


    }


    @Override
    public Collection<StudentDto> getAllCourseClassStudents(Long courseClassId) {
        Collection<CourseRegister> courseRegisters = courseRegisterRepository
                .findAllByCourseClass_CourseClassId(courseClassId);
        Collection<Student> courseClassStudents = courseRegisters.stream()
                .map(CourseRegister::getStudent).collect(Collectors.toList());
        return courseClassStudents.stream().map(studentDtoIMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public Collection<CourseRegisterOutDto> getMyReslut(YearSemester semester, String year) {
        User user = iAuthenticationFacade.getAuthenticatedUser();

        List<CourseRegisterOutDto> courseRegisterOutDtos = courseRegisterRepository
                .findByCourseClassYearAndCourseClassCourseSemesterAndStudentIdAndStateIn(year , semester , user.getId() , List.of(CourseRegisterState.PASSED, CourseRegisterState.FAILED))
                .stream().map(courseRegisterOutDtoMapper::mapTo).toList();

        if (courseRegisterOutDtos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No course registrations found for student with code: " + user.getUsername());
        }
        System.out.println(courseRegisterOutDtos);
        return courseRegisterOutDtos;

    }


    @Override
    public Collection<CourseRegisterOutDto> getCourseRegistersByState(String studentCode, CourseRegisterState state) {
        //check if student exists
        studentRepository.findByCode(studentCode)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));

        //return course registers by state
        Collection<CourseRegister> courseRegisters = courseRegisterRepository.findAllByStudent_CodeAndState(studentCode, state);
        if (courseRegisters.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No course registrations found for student with code: " + studentCode);
        }
        return courseRegisters
                .stream()
                .map(courseRegisterResponseDtoMapper::mapTo)
                .toList();
    }


    @Override
    public Collection<CourseRegisterOutDto> getStudentRegisteredCourses(String studentCode) {
        Student student = studentRepository.findByCode(studentCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));
        return courseRegisterRepository.findAllByStudentId(student.getId())
                .stream().map(courseRegisterOutDtoMapper::mapTo).collect(Collectors.toList());
    }

}
