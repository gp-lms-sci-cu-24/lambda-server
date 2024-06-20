package com.cu.sci.lambdaserver.courseclass.dto;

import com.cu.sci.lambdaserver.location.dto.LocationDto;
import com.cu.sci.lambdaserver.utils.enums.ClassType;
import lombok.Builder;

import java.sql.Time;
import java.time.DayOfWeek;

@Builder
public record CourseClassTimingDto(
        Long id,
        DayOfWeek day,
        Time startTime,
        Time endTime,
        LocationDto location,
        ClassType type
) {
}
