package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.courseclass.service.CourseClassService;
import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.CourseRegisterRepository;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterResponseDto;
import com.cu.sci.lambdaserver.courseregister.mapper.CourseRegisterInDtoMapper;
import com.cu.sci.lambdaserver.courseregister.mapper.CourseRegisterOutDtoMapper;
import com.cu.sci.lambdaserver.courseregister.mapper.CourseRegisterResponseMapper;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseRegisterService implements ICourseRegisterService {

    private final CourseRegisterRepository courseRegisterRepository;
    private final StudentRepository studentRepository;
    private final CourseClassRepository courseClassRepository;
    private final CourseClassService courseClassService;

    private final CourseRegisterInDtoMapper courseRegisterInDtoMapper;
    private final CourseRegisterOutDtoMapper courseRegisterOutDtoMapper;
    private final CourseRegisterResponseMapper courseRegisterResponseDtoMapper;
    private final IMapper<Student, StudentDto> studentDtoIMapper;
    private final IAuthenticationFacade iAuthenticationFacade;


    /**
     * Create a new course register
     *  check if course register already exists
     * @RequestBody courseRegisterInDto
     * @return CourseRegisterResponseDto
     */
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

    void assignGradeToCourseRegister(Long grade, CourseRegister courseRegister) {

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
    public CourseRegisterResponseDto createCourseRegister(CourseRegisterInDto courseRegisterInDto) {

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


        //check course class exists
        Optional<CourseRegister> courseRegister = courseRegisterRepository.findByCourseClass_CourseClassIdAndStudentId(courseRegisterInDto.getCourseClassId(),student.getId());
        if(courseRegister.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "course register already exists");
        }

        //get Course Class
        CourseClass courseClass = courseClassService.getCourseClassById(courseRegisterInDto.getCourseClassId());

        //check course class state
        if (!courseClass.getCourseState().equals(State.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course Class is not active");
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
    public CourseRegisterOutDto updateCourseRegister(CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterRepository.findById(courseRegisterInDto.getCourseRegisterId())
                .map(courseRegister -> {
                    courseRegisterInDtoMapper.update(courseRegisterInDto, courseRegister);
                    return courseRegisterOutDtoMapper.mapTo(courseRegisterRepository.save(courseRegister));
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found with this id"));
    }


    @Override
    @Transactional
    public CourseRegisterOutDto deleteCourseRegister(Long id) {
        CourseRegister courseRegister = courseRegisterRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found with this id"));
        Student student = courseRegister.getStudent();
        CourseClass courseClass = courseRegister.getCourseClass();

        student.setCreditHoursSemester(student.getCreditHoursSemester() - courseClass.getCourse().getCreditHours());
        studentRepository.save(student);
        courseRegisterRepository.deleteById(id);

        return courseRegisterOutDtoMapper.mapTo(courseRegister);
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
    public Collection<CourseRegisterResponseDto> getCourseRegistersByState(String studentCode, CourseRegisterState state) {
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
