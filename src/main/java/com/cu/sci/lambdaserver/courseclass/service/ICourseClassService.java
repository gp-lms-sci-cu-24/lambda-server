package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.CourseClass;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;

import java.util.List;
import java.util.Optional;
public interface ICourseClassService {
    CourseClass createCourseClass(CourseClass courseClass);

    List<CourseClass> getAllCourseClasses();

    Optional<CourseClass> getCourseClassById(Long id);

    boolean isCourseClassExists(Long id);

    CourseClass updateCourseClass(CourseClassDto courseClassDto);

    void deleteCourseClass(Long id);
}
