package com.cu.sci.lambdaserver.courseclasstiming.service;

import com.cu.sci.lambdaserver.courseclasstiming.CourseClassTiming;

import java.util.List;

public interface ICourseClassTimingServices {
    CourseClassTiming addCourseClassTiming(CourseClassTiming courseClassTiming);

    List<CourseClassTiming> getAllCourseClassTiming();

    CourseClassTiming getCourseClassTimingById(Long id);

    CourseClassTiming updateCourseClassTiming(Long id, CourseClassTiming courseClassTiming);

    CourseClassTiming deleteCourseClassTiming(Long id);

}
