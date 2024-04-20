package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;

public interface ICourseRegisterService {
    @PreAuthorize("hasRole('STUDENT')")
    CourseRegister studentCreateCourseRegister(CourseRegisterInDto courseRegisterInDto);
    @PreAuthorize("hasRole('ADMIN')")
    CourseRegister createCourseRegister(CourseRegisterInDto courseRegisterInDto);

    Page<CourseRegister> getAllCourseRegisters(Integer pageNo, Integer pageSize);
    @PreAuthorize("hasRole('STUDENT')")
    Collection<CourseRegister> studentGetAllCourseRegisters();
    CourseRegister getCourseRegister(Long id);

    CourseRegister updateCourseRegister(CourseRegisterInDto courseRegisterInDto);

    Collection<CourseRegister> getStudentRegisteredCourses(String studentCode);
    CourseRegister deleteCourseRegister(Long id);
}