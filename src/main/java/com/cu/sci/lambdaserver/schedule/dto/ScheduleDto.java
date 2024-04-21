package com.cu.sci.lambdaserver.schedule.dto;

import com.cu.sci.lambdaserver.location.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    String lecCode;
    String day;
    Long startTime;
    Long endTime;
    LocationDto location;
    String courseCode;
    String courseGroup;
}
