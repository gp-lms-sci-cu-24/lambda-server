package com.cu.sci.lambdaserver.schedule;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleMapper {
    public ScheduleDto map(CourseDto course, CourseClassTimingDto timing, Integer courseGroup) {
        return ScheduleDto.builder()
                .day(timing.day())
                .startTime(timing.startTime().toString())
                .endTime(timing.endTime().toString())
                .location(timing.location())
                .classType(timing.type())
                .course(course)
                .courseGroup(courseGroup)
                .build();
    }
}
