package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ICourseClassService {

    @PreAuthorize("hasRole('ADMIN')")
    CourseClassDto createCourseClass(CreateCourseClassDto courseClass);

    CourseClassDto getCourseClass(String courseCode , Integer groupNumber);

    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse deleteCourseClass(String courseCode , Integer groupNumber);

    CourseClass updateCourseClass(CourseClassDto courseClassDto);

}
