package com.cu.sci.lambdaserver.courseClassTiming;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseClassTimingServices implements ICourseClassTimingServices {
    private CourseClassTimingRepository courseClassTimingRepository;

    @Override
    public CourseClassTiming addCourseClassTiming(CourseClassTiming courseClassTiming) {
        List<CourseClassTiming> collision = courseClassTimingRepository.findByLocationAndStartTimeBetween(
                courseClassTiming.getLocation(),
                courseClassTiming.getStartTime(),
                courseClassTiming.getEndTime()
        );
        if (collision.isEmpty())
            return courseClassTimingRepository.save(courseClassTiming);
        else
            throw new IllegalStateException("this class will make collision with " + collision);
    }

    @Override
    public List<CourseClassTiming> getAllCourseClassTiming() {
        return courseClassTimingRepository.findAll();
    }

    @Override
    public Optional<CourseClassTiming> getCourseClassTimingById(Long id) {
        return courseClassTimingRepository.findById(id);
    }

    @Override
    public CourseClassTiming updateCourseClassTiming(Long id, CourseClassTiming courseClassTiming) {
        CourseClassTiming courseClassTiming1 = courseClassTimingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CourseClass Not Found With This Id " + id));
        courseClassTimingRepository.delete(courseClassTiming1);
        return courseClassTimingRepository.save(courseClassTiming);
    }

    @Override
    public CourseClassTiming deleteCourseClassTiming(Long id) {
        CourseClassTiming courseClassTiming = courseClassTimingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CourseClass Not Found With This Id " + id));
        courseClassTimingRepository.deleteById(id);
        return courseClassTiming;
    }
}
