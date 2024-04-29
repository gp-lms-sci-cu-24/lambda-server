package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.service.CourseClassService;
import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.CourseRegisterRepository;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.courseregister.mapper.CourseRegisterInDtoMapper;
import com.cu.sci.lambdaserver.courseregister.mapper.CourseRegisterOutDtoMapper;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseRegisterService implements ICourseRegisterService {

    private final CourseRegisterRepository courseRegisterRepository;
    private final StudentRepository studentRepository;
    private final CourseClassService courseClassService;

    private final CourseRegisterInDtoMapper courseRegisterInDtoMapper;
    private final CourseRegisterOutDtoMapper courseRegisterOutDtoMapper;
    private final IMapper<Student, StudentDto> studentDtoIMapper;
    private final IAuthenticationFacade iAuthenticationFacade;

    public CourseRegister initNewCourseRegister(CourseClass courseClass, Student student) {
        CourseRegister courseRegister = new CourseRegister();
        student.setCreditHoursSemester(student.getCreditHoursSemester() + courseClass.getCourse().getCreditHours());
        courseRegister.setCourseClass(courseClass);
        courseRegister.setStudent(student);
        return courseRegister;
    }
    @Override
    public CourseRegisterOutDto studentCreateCourseRegister(CourseRegisterInDto courseRegisterInDto) {
        User user = iAuthenticationFacade.getAuthenticatedUser();
        Student student = studentRepository.findById(user.getId())
                .orElseThrow();
        CourseClass courseClass = courseClassService
                .getCourseClassById(courseRegisterInDto.getCourseClassId());
//        CourseRegister courseRegister = new CourseRegister();
//        courseRegister.setCourseClass(courseClass);
//        courseRegister.setStudent(student);

        return courseRegisterOutDtoMapper
                .mapTo(courseRegisterRepository.save(initNewCourseRegister(courseClass, student)));
    }

    @Override
    public CourseRegisterOutDto createCourseRegister(CourseRegisterInDto courseRegisterInDto) {

        String studentCode = courseRegisterInDto.getStudentCode();
        Long courseClassId = courseRegisterInDto.getCourseClassId();
        Student student = studentRepository.findByCode(studentCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));
        CourseClass courseClass = courseClassService.getCourseClassById(courseClassId);

//        CourseRegister courseRegister = new CourseRegister();
//        courseRegister.setCourseClass(courseClass);
//        courseRegister.setStudent(student);

        return courseRegisterOutDtoMapper
                .mapTo(courseRegisterRepository.save(initNewCourseRegister(courseClass, student)));
    }

    @Override
    public Page<CourseRegisterOutDto> getAllCourseRegisters(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return courseRegisterRepository.findAll(pageable).map(courseRegisterOutDtoMapper::mapTo);
    }
    @Override
    public Collection<CourseRegisterOutDto> studentGetAllCourseRegisters() {
        User user = iAuthenticationFacade.getAuthenticatedUser();
        return courseRegisterRepository.findAllByStudentId(user.getId()).stream()
                .map(courseRegisterOutDtoMapper::mapTo).collect(Collectors.toList());
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
    public Collection<CourseRegisterOutDto> getStudentRegisteredCourses(String studentCode) {
        Student student = studentRepository.findByCode(studentCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));
        return courseRegisterRepository.findAllByStudentId(student.getId())
                .stream().map(courseRegisterOutDtoMapper::mapTo).collect(Collectors.toList());
    }
}
