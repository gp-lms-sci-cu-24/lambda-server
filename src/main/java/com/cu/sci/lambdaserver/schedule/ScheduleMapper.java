package com.cu.sci.lambdaserver.schedule;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleMapper {
    public ScheduleDto map(CourseClassDto courseClass, CourseClassTimingDto timing) {
        return ScheduleDto.builder()
                .day(timing.day())
                .startTime(timing.startTime().toString())
                .endTime(timing.endTime().toString())
                .location(timing.location())
                .classType(timing.type())
                .course(courseClass.getCourse())
                .courseGroup(courseClass.getGroupNumber())
                .professor(courseClass.getAdminProfessor())
                .build();
    }
}
