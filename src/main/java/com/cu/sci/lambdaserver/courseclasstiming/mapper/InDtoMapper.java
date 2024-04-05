package com.cu.sci.lambdaserver.courseclasstiming.mapper;

import com.cu.sci.lambdaserver.courseclass.CourseClass;
import com.cu.sci.lambdaserver.courseclasstiming.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclasstiming.dto.CourseClassTimingInDto;
import com.cu.sci.lambdaserver.location.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InDtoMapper {

    public CourseClassTiming mapTo(CourseClassTimingInDto courseClassTimingInDto, Location location, CourseClass courseClass) {
        return CourseClassTiming.builder().location(location)
                .courseclass(courseClass)
                .day(courseClassTimingInDto.getDay())
                .startTime(courseClassTimingInDto.getStartTime())
                .endTime(courseClassTimingInDto.getEndTime())
                .type(courseClassTimingInDto.getType())
                .build();
    }

    public CourseClassTimingInDto mapFrom(CourseClassTiming courseClassTiming) {
        return CourseClassTimingInDto.builder().
                courseClassId(courseClassTiming.getCourseclass().getCourseClassId())
                .day(courseClassTiming.getDay())
                .endTime(courseClassTiming.getEndTime())
                .startTime(courseClassTiming.getStartTime())
                .locationId(courseClassTiming.getLocation().getLocationId())
                .type(courseClassTiming.getType())
                .build();
    }
}
