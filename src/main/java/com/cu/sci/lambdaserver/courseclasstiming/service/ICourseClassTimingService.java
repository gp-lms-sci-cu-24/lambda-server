package com.cu.sci.lambdaserver.courseclasstiming.service;

import com.cu.sci.lambdaserver.courseclasstiming.dto.CourseClassTimingInDto;
import com.cu.sci.lambdaserver.courseclasstiming.dto.CourseClassTimingOutDto;

import java.util.List;

public interface ICourseClassTimingService {
    CourseClassTimingOutDto addCourseClassTiming(CourseClassTimingInDto courseClassTimingInDto);

    List<CourseClassTimingOutDto> getAllCourseClassTiming();

    CourseClassTimingOutDto getCourseClassTimingById(Long id);

    CourseClassTimingOutDto updateCourseClassTiming(Long id, CourseClassTimingInDto courseClassTimingInDto);

    CourseClassTimingOutDto deleteCourseClassTiming(Long id);

}
