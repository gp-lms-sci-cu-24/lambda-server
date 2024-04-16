package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingInDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingOutDto;

import java.util.List;

public interface ICourseClassTimingService {
    CourseClassTimingOutDto addCourseClassTiming(CourseClassTimingInDto courseClassTimingInDto);

    List<CourseClassTimingOutDto> getAllCourseClassTiming();

    CourseClassTimingOutDto getCourseClassTimingById(Long id);

    CourseClassTimingOutDto updateCourseClassTiming(Long id, CourseClassTimingInDto courseClassTimingInDto);

    CourseClassTimingOutDto deleteCourseClassTiming(Long id);

}
