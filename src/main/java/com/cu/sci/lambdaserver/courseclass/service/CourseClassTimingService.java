package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingInDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingOutDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclass.mapper.TimingInDtoMapper;
import com.cu.sci.lambdaserver.courseclass.mapper.TimingOutDtoMapper;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassTimingRepository;
import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.location.LocationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseClassTimingService implements ICourseClassTimingService {
    private final CourseClassTimingRepository courseClassTimingRepository;
    private final LocationRepository locationRepository;
    private final CourseClassRepository courseClassRepository;
    private final TimingInDtoMapper timingInDtoMapper;
    private final TimingOutDtoMapper timingOutDtoMapper;

    private List<CourseClassTiming> getCollsionList(Location location, DayOfWeek day, Long startTime, Long endTime) {
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
    public CourseClassTimingOutDto addCourseClassTiming(@Valid CourseClassTimingInDto courseClassTimingInDto) {
        if (courseClassTimingInDto.getStartTime() >= courseClassTimingInDto.getEndTime())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "invalid time range end time should be greater than start time");

        Optional<Location> location = locationRepository.findById(courseClassTimingInDto.getLocationId());
        if (location.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "location not found with this id " + courseClassTimingInDto.getLocationId());

        List<CourseClassTiming> collision = getCollsionList(
                location.get(),
                courseClassTimingInDto.getDay(),
                courseClassTimingInDto.getStartTime(),
                courseClassTimingInDto.getEndTime()
        );
        if (!collision.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this class will make collision with " + buildConflictError(collision));
        CourseClassTiming courseClassTiming = timingInDtoMapper.mapTo(courseClassTimingInDto, location.get(), null);
        courseClassTimingRepository.save(courseClassTiming);
        return timingOutDtoMapper.mapTo(courseClassTiming);
    }

    @Override
    public List<CourseClassTimingOutDto> getAllCourseClassTiming() {
        return courseClassTimingRepository.findAll().stream().map(timingOutDtoMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public CourseClassTimingOutDto getCourseClassTimingById(Long id) {
        Optional<CourseClassTiming> courseClassTiming = courseClassTimingRepository.findById(id);
        if (courseClassTiming.isPresent())
            return timingOutDtoMapper.mapTo(courseClassTiming.get());
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " class timing not found ");
    }

    @Override
    public CourseClassTimingOutDto updateCourseClassTiming(Long id, CourseClassTimingInDto newCourseClassTiming) {
        CourseClassTiming courseClassTiming = courseClassTimingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CourseClass Not Found With This Id " + id));

        Optional<Location> location = locationRepository.findById(newCourseClassTiming.getLocationId());
        if (location.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "location not found with this id " + newCourseClassTiming.getLocationId());


        List<CourseClassTiming> collision = getCollsionList(
                location.get(),
                newCourseClassTiming.getDay(),
                newCourseClassTiming.getStartTime(),
                newCourseClassTiming.getEndTime()
        );
        if (!collision.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this new timing  will make collision with " + buildConflictError(collision));

        courseClassTiming.setLocation(location.get());
        courseClassTiming.setStartTime(newCourseClassTiming.getStartTime());
        courseClassTiming.setEndTime(newCourseClassTiming.getEndTime());
        courseClassTiming.setDay(newCourseClassTiming.getDay());
        courseClassTiming.setType(newCourseClassTiming.getType());
        return timingOutDtoMapper.mapTo(courseClassTimingRepository.save(courseClassTiming));
    }

    @Override
    public CourseClassTimingOutDto deleteCourseClassTiming(Long id) {
        CourseClassTiming courseClassTiming = courseClassTimingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "timing Not Found With This Id " + id));
        courseClassTimingRepository.deleteById(id);
        return timingOutDtoMapper.mapTo(courseClassTiming);
    }
}
