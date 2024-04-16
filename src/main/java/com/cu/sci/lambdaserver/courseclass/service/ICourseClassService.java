package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;

import java.util.List;

public interface ICourseClassService {
    CourseClass createCourseClass(CourseClass courseClass);

    List<CourseClass> getAllCourseClasses();

    CourseClass getCourseClassById(Long id);

    boolean isCourseClassExists(Long id);

    CourseClass updateCourseClass(CourseClassDto courseClassDto);

    void deleteCourseClass(Long id);
}
