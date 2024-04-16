package com.cu.sci.lambdaserver.courseclass.dto;

import com.cu.sci.lambdaserver.utils.enums.ClassType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseClassTimingInDto {
    private Long id;

    //    @NotBlank(message = "course class should be defined to create schedule a timing")
    private Long courseClassId;

    private ClassType type;

    @NotBlank(message = "day is mandatory to know availability of location")
    private String day;


    //    @NotBlank(message = "start time is mandatory to know availability of location")
    @Min(value = 0, message = "start time should be within 24hr format ")
    @Max(value = 24, message = "end time should be within 24hr format")
    private Long startTime;

    //    @NotBlank(message = "end time is mandatory to know availability of location")
    @Min(value = 0, message = "end time should be within 24hr format ")
    @Max(value = 24, message = "end time should be within 24hr format")
    private Long endTime;

    private Long locationId;
}
