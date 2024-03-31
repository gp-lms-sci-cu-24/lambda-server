package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.CourseClass;
import java.util.List;
import java.util.Optional;
public interface iCourseClassService {
    CourseClass createCourseClass(CourseClass courseClass);

    List<CourseClass> getAllCourseClasses();

    Optional<CourseClass> getCourseClassById(Long id);

    boolean isCourseClassExists(Long id);

    CourseClass updateCourseClass(Long id, CourseClass courseClass);

    void deleteCourseClass(Long id);
}
