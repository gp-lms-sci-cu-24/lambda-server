package com.cu.sci.lambdaserver.courseclasstiming.service;

import com.cu.sci.lambdaserver.courseclass.CourseClass;
import com.cu.sci.lambdaserver.courseclass.CourseClassRepository;
import com.cu.sci.lambdaserver.courseclasstiming.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclasstiming.CourseClassTimingRepository;
import com.cu.sci.lambdaserver.courseclasstiming.dto.CourseClassTimingInDto;
import com.cu.sci.lambdaserver.courseclasstiming.dto.CourseClassTimingOutDto;
import com.cu.sci.lambdaserver.courseclasstiming.mapper.InDtoMapper;
import com.cu.sci.lambdaserver.courseclasstiming.mapper.OutDtoMapper;
import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.location.LocationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseClassTimingService implements ICourseClassTimingService {
    private final CourseClassTimingRepository courseClassTimingRepository;
    private final LocationRepository locationRepository;
    private final CourseClassRepository courseClassRepository;
    private final InDtoMapper inDtoMapper;
    private final OutDtoMapper outDtoMapper;

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
    public CourseClassTimingOutDto addCourseClassTiming(@Valid CourseClassTimingInDto courseClassTimingInDto) {
        if (courseClassTimingInDto.getStartTime() >= courseClassTimingInDto.getEndTime())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "invalid time range end time should be greater than start time");

        Optional<Location> location = locationRepository.findById(courseClassTimingInDto.getLocationId());
        if (location.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "location not found with this id " + courseClassTimingInDto.getLocationId());

        Optional<CourseClass> courseClass = courseClassRepository.findById(courseClassTimingInDto.getCourseClassId());
        if (courseClass.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this id " + courseClassTimingInDto.getCourseClassId());

        List<CourseClassTiming> collision = getCollsionList(
                location.get(),
                courseClassTimingInDto.getDay(),
                courseClassTimingInDto.getStartTime(),
                courseClassTimingInDto.getEndTime()
        );
        if (!collision.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this class will make collision with " + buildConflictError(collision));
        CourseClassTiming courseClassTiming = inDtoMapper.mapTo(courseClassTimingInDto, location.get(), courseClass.get());
        courseClassTimingRepository.save(courseClassTiming);
        return outDtoMapper.mapTo(courseClassTiming);
    }

    @Override
    public List<CourseClassTimingOutDto> getAllCourseClassTiming() {
        return courseClassTimingRepository.findAll().stream().map(outDtoMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public CourseClassTimingOutDto getCourseClassTimingById(Long id) {
        Optional<CourseClassTiming> courseClassTiming = courseClassTimingRepository.findById(id);
        if (courseClassTiming.isPresent())
            return outDtoMapper.mapTo(courseClassTiming.get());
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

        Optional<CourseClass> courseClass = courseClassRepository.findById(newCourseClassTiming.getCourseClassId());
        if (courseClass.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this id " + newCourseClassTiming.getCourseClassId());

        List<CourseClassTiming> collision = getCollsionList(
                location.get(),
                newCourseClassTiming.getDay(),
                newCourseClassTiming.getStartTime(),
                newCourseClassTiming.getEndTime()
        );
        if (!collision.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this new timing  will make collision with " + buildConflictError(collision));

        courseClassTiming.setLocation(location.get());
        courseClassTiming.setCourseclass(courseClass.get());
        courseClassTiming.setStartTime(newCourseClassTiming.getStartTime());
        courseClassTiming.setEndTime(newCourseClassTiming.getEndTime());
        courseClassTiming.setDay(newCourseClassTiming.getDay());
        courseClassTiming.setType(newCourseClassTiming.getType());
        return outDtoMapper.mapTo(courseClassTimingRepository.save(courseClassTiming));
    }

    @Override
    public CourseClassTimingOutDto deleteCourseClassTiming(Long id) {
        CourseClassTiming courseClassTiming = courseClassTimingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CourseClass Not Found With This Id " + id));
        courseClassTimingRepository.deleteById(id);
        return outDtoMapper.mapTo(courseClassTiming);
    }
}
