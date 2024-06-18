package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassRequestDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ICourseClassService {

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    CourseClassDto createCourseClass(CreateCourseClassRequestDto courseClass);

    @PreAuthorize("hasRole('ADMIN')")
    Collection<CourseClassDto> getAllCourseClasses();

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN','ACADEMIC_ADVISOR')")
    CourseClassDto getCourseClass(String courseCode , Integer groupNumber);

    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse deleteCourseClass(String courseCode , Integer groupNumber);

    @PreAuthorize("hasRole('ADMIN')")
    CourseClassDto updateCourseClass(String courseCode, Integer groupNumber, CreateCourseClassDto courseClass);

    @PreAuthorize("hasRole('ADMIN')")
    Collection<CourseClassDto> getCourseClassesByCourseCodeAndSemester(String courseCode, YearSemester semester, Integer Year);

}
