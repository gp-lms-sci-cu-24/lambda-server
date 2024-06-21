package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;

public interface ICourseClassTimingService {
    boolean isIntersect(CourseClassTiming time1, CourseClassTiming time2);

//    CourseClassTimingOutDto addCourseClassTiming(CourseClassTimingInDto courseClassTimingInDto);
//
//    List<CourseClassTimingOutDto> getAllCourseClassTiming();
//
//    CourseClassTimingOutDto getCourseClassTimingById(Long id);
//
//    CourseClassTimingOutDto updateCourseClassTiming(Long id, CourseClassTimingInDto courseClassTimingInDto);
//
//    CourseClassTimingOutDto deleteCourseClassTiming(Long id);

}
