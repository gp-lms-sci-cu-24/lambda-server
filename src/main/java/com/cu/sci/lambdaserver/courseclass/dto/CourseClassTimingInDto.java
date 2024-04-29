package com.cu.sci.lambdaserver.courseclass.dto;

import com.cu.sci.lambdaserver.utils.enums.ClassType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.DayOfWeek;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseClassTimingInDto {
    private Long id;

    //    @NotBlank(message = "course class should be defined to create schedule a timing")
    private Long courseClassId;

    private ClassType type;

    @NotNull(message = "day is mandatory to know availability of location")
    private DayOfWeek day;


    @NotNull(message = "start time is mandatory to know availability of location")
    private Time startTime;

    @NotNull(message = "end time is mandatory to know availability of location")
    private Time endTime;

    private Long locationId;
}
