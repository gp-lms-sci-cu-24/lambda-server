package com.cu.sci.lambdaserver.courseclasstiming.service;

import com.cu.sci.lambdaserver.courseclasstiming.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclasstiming.CourseClassTimingRepository;
import com.cu.sci.lambdaserver.location.Location;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseClassTimingServices implements ICourseClassTimingServices {
    private CourseClassTimingRepository courseClassTimingRepository;

    @Autowired
    public CourseClassTimingServices(CourseClassTimingRepository courseClassTimingRepository) {
        this.courseClassTimingRepository = courseClassTimingRepository;
    }

    private List<CourseClassTiming> collsionList(Location location, String day, Long startTime, Long endTime) {
        return courseClassTimingRepository.findByLocationAndDayAndStartTimeLessThanAndEndTimeGreaterThan(
                location,
                day,
                endTime,
                startTime
        );
    }

    @Override
    public CourseClassTiming addCourseClassTiming(@Valid CourseClassTiming courseClassTiming) {
        List<CourseClassTiming> collision = collsionList(
                courseClassTiming.getLocation(),
                courseClassTiming.getDay(),
                courseClassTiming.getStartTime(),
                courseClassTiming.getEndTime()
        );
        if (collision.isEmpty())
            return courseClassTimingRepository.save(courseClassTiming);
        else {
            StringBuilder error = new StringBuilder();
            for (CourseClassTiming classTiming : collision) {
                error.append(classTiming.getId()).append(" which is between ").append(classTiming.getStartTime()).append(" ").append(classTiming.getEndTime()).append(" \n ");
            }
            throw new IllegalStateException("this class will make collision with " + error);
        }
    }

    @Override
    public List<CourseClassTiming> getAllCourseClassTiming() {
        return courseClassTimingRepository.findAll();
    }

    @Override
    public CourseClassTiming getCourseClassTimingById(Long id) {
        Optional<CourseClassTiming> courseClassTiming = courseClassTimingRepository.findById(id);
        if (courseClassTiming.isPresent())
            return courseClassTiming.get();
        else
            throw new IllegalStateException("class timing not present ");
    }

    @Override
    public CourseClassTiming updateCourseClassTiming(Long id, CourseClassTiming newCourseClassTiming) {
        CourseClassTiming courseClassTiming = courseClassTimingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CourseClass Not Found With This Id " + id));
        courseClassTiming.setStartTime(newCourseClassTiming.getStartTime());
        List<CourseClassTiming> collision = collsionList(
                newCourseClassTiming.getLocation(),
                newCourseClassTiming.getDay(),
                newCourseClassTiming.getStartTime(),
                newCourseClassTiming.getEndTime()
        );
        if (collision.isEmpty()) {
            courseClassTiming.setStartTime(newCourseClassTiming.getStartTime());
            courseClassTiming.setEndTime(newCourseClassTiming.getEndTime());
            courseClassTiming.setLocation(newCourseClassTiming.getLocation());
            courseClassTiming.setDay(newCourseClassTiming.getDay());
        } else
            System.out.println("couldn't change time because of collisions");
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
