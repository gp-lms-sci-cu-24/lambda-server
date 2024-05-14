package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ICourseClassService {

    @PreAuthorize("hasRole('ADMIN')")
    CourseClassDto createCourseClass(CreateCourseClassDto courseClass);

    CourseClassDto getCourseClass(String courseCode , Integer groupNumber);

    boolean isCourseClassExists(Long id);

    CourseClass updateCourseClass(CourseClassDto courseClassDto);

    void deleteCourseClass(Long id);
}
