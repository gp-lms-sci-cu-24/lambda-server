package com.cu.sci.lambdaserver.courseclasstiming.service;

import com.cu.sci.lambdaserver.courseclass.CourseClassRepository;
import com.cu.sci.lambdaserver.courseclasstiming.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclasstiming.CourseClassTimingRepository;
import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.location.LocationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseClassTimingServices implements ICourseClassTimingServices {
    private final CourseClassTimingRepository courseClassTimingRepository;
    private final LocationRepository locationRepository;
    private final CourseClassRepository courseClassRepository;

    private List<CourseClassTiming> getCollsionList(Location location, String day, Long startTime, Long endTime) {
        return courseClassTimingRepository.findByLocationAndDayAndStartTimeLessThanAndEndTimeGreaterThan(
                location,
                day,
                endTime,
                startTime
        );
    }

    String buildConflictError(List<CourseClassTiming> collision) {
        StringBuilder error = new StringBuilder();
        for (CourseClassTiming classTiming : collision) {
            error.append(classTiming.getId())
                    .append(" which is between ")
                    .append(classTiming.getStartTime())
                    .append(" ")
                    .append(classTiming.getEndTime())
                    .append(" \n ");
        }
        return error.toString();
    }

    @Override
    public CourseClassTiming addCourseClassTiming(@Valid CourseClassTiming courseClassTiming) {
        List<CourseClassTiming> collision = getCollsionList(
                courseClassTiming.getLocation(),
                courseClassTiming.getDay(),
                courseClassTiming.getStartTime(),
                courseClassTiming.getEndTime()
        );
        if (!collision.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this class will make collision with " + buildConflictError(collision));
        return courseClassTimingRepository.save(courseClassTiming);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " class timing not found ");
    }

    @Override
    public CourseClassTiming updateCourseClassTiming(Long id, CourseClassTiming newCourseClassTiming) {
        CourseClassTiming courseClassTiming = courseClassTimingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CourseClass Not Found With This Id " + id));
        courseClassTiming.setStartTime(newCourseClassTiming.getStartTime());
        List<CourseClassTiming> collision = getCollsionList(
                newCourseClassTiming.getLocation(),
                newCourseClassTiming.getDay(),
                newCourseClassTiming.getStartTime(),
                newCourseClassTiming.getEndTime()
        );
        if (!collision.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this new timing  will make collision with " + buildConflictError(collision));

        courseClassTiming.setStartTime(newCourseClassTiming.getStartTime());
        courseClassTiming.setEndTime(newCourseClassTiming.getEndTime());
        courseClassTiming.setLocation(newCourseClassTiming.getLocation());
        courseClassTiming.setDay(newCourseClassTiming.getDay());
        return courseClassTimingRepository.save(courseClassTiming);
    }

    @Override
    public CourseClassTiming deleteCourseClassTiming(Long id) {
        CourseClassTiming courseClassTiming = courseClassTimingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CourseClass Not Found With This Id " + id));
        courseClassTimingRepository.deleteById(id);
        return courseClassTiming;
    }
}
