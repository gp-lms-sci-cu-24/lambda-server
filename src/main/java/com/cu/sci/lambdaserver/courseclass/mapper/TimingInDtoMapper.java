package com.cu.sci.lambdaserver.courseclass.mapper;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingInDto;
import com.cu.sci.lambdaserver.location.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimingInDtoMapper {

    public CourseClassTiming mapTo(CourseClassTimingInDto courseClassTimingInDto, Location location, CourseClass courseClass) {
        return CourseClassTiming.builder().location(location)
                .day(courseClassTimingInDto.getDay())
                .startTime(courseClassTimingInDto.getStartTime())
                .endTime(courseClassTimingInDto.getEndTime())
                .type(courseClassTimingInDto.getType())
                .build();
    }

    public CourseClassTimingInDto mapFrom(CourseClassTiming courseClassTiming) {
        return CourseClassTimingInDto.builder()
                .day(courseClassTiming.getDay())
                .endTime(courseClassTiming.getEndTime())
                .startTime(courseClassTiming.getStartTime())
                .locationId(courseClassTiming.getLocation().getLocationId())
                .type(courseClassTiming.getType())
                .build();
    }
}
