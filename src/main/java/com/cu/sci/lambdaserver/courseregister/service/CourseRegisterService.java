package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.courseclass.CourseClass;
import com.cu.sci.lambdaserver.courseclass.service.CourseClassService;
import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.CourseRegisterRepository;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.courseregister.mapper.CourseRegisterInDtoMapper;
import com.cu.sci.lambdaserver.courseregister.mapper.CourseRegisterOutDtoMapper;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import com.cu.sci.lambdaserver.utils.enums.State;
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
    @Override
    public CourseRegister createCourseRegister(CourseRegisterInDto courseRegisterInDto) {
        String studentCode = courseRegisterInDto.getStudentCode();
        Long courseClassId = courseRegisterInDto.getCourseClassId();
        Student student = studentRepository.findByCode(studentCode)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code") );
        CourseClass courseClass = courseClassService.getCourseClassById(courseClassId);

        CourseRegister courseRegister = new CourseRegister();
        courseRegister.setCourseClass(courseClass);
        courseRegister.setStudent(student);
//        System.out.println(student);
//        System.out.println(courseClass);
//        System.out.println(courseRegister);
        return courseRegisterRepository.save(courseRegister);
    }

    @Override
    public Page<CourseRegister> getAllCourseRegisters(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return courseRegisterRepository.findAll(pageable);
    }

    @Override
    public CourseRegister getCourseRegister(Long id) {
        return courseRegisterRepository.findById(id)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found with this id") );
    }

    @Override
    public CourseRegister updateCourseRegister(CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterRepository.findById(courseRegisterInDto.getCourseRegisterId() )
            .map(courseRegister -> {
                courseRegisterInDtoMapper.update(courseRegisterInDto, courseRegister);
                return courseRegisterRepository.save(courseRegister);
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found with this id") );
    }

    @Override
    public CourseRegister deleteCourseRegister(Long id) {
        CourseRegister courseRegister = getCourseRegister(id);
        courseRegisterRepository.deleteById(id);
        return courseRegister;
    }
    public Collection<CourseClass> findStudentRegisteredCourses(String studentCode){
        Collection<CourseRegister> registeredCourses = courseRegisterRepository
                .findCourseRegisterByStudent_Code(studentCode );
        Collection<CourseClass> registeredCourseClasses = registeredCourses
            .stream()
            .map(CourseRegister::getCourseClass)
            .collect(Collectors.toList());
        return registeredCourseClasses;
    }
    public Collection<CourseClass> findStudentRegisteredCoursesBySemester(String studentCode, State state){
        Collection<CourseRegister> registeredCourses = courseRegisterRepository
                .findCourseRegisterByStudent_CodeAndCourseClass_CourseState(studentCode, state);

        Collection<CourseClass> registeredCourseClasses = registeredCourses
                .stream()
                .map(CourseRegister::getCourseClass)
                .collect(Collectors.toList());
        return registeredCourseClasses;
    }
}
