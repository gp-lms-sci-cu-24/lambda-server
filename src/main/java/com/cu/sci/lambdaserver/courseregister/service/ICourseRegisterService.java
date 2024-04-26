package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;

public interface ICourseRegisterService {
    @PreAuthorize("hasRole('STUDENT')")
    CourseRegisterOutDto studentCreateCourseRegister(CourseRegisterInDto courseRegisterInDto);

    @PreAuthorize("hasRole('ADMIN')")
    CourseRegisterOutDto createCourseRegister(CourseRegisterInDto courseRegisterInDto);

    Page<CourseRegisterOutDto> getAllCourseRegisters(Integer pageNo, Integer pageSize);

    @PreAuthorize("hasRole('STUDENT')")
    Collection<CourseRegisterOutDto> studentGetAllCourseRegisters();

    CourseRegisterOutDto getCourseRegister(Long id);

    CourseRegisterOutDto updateCourseRegister(CourseRegisterInDto courseRegisterInDto);

    Collection<CourseRegisterOutDto> getStudentRegisteredCourses(String studentCode);

    CourseRegisterOutDto deleteCourseRegister(Long id);

    Collection<StudentDto> getAllCourseClassStudents(Long courseClassId);
}