package com.cu.sci.lambdaserver.courseClassTiming;

import java.util.List;
import java.util.Optional;

public interface ICourseClassTimingServices {
    CourseClassTiming addCourseClassTiming(CourseClassTiming courseClassTiming);

    List<CourseClassTiming> getAllCourseClassTiming();

    Optional<CourseClassTiming> getCourseClassTimingById(Long id);

    CourseClassTiming updateCourseClassTiming(Long id, CourseClassTiming courseClassTiming);

    CourseClassTiming deleteCourseClassTiming(Long id);
    
}
