package com.cu.sci.lambdaserver.course.dto;


import com.cu.sci.lambdaserver.utils.enums.Semester;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseDto {

    @NotNull(message = "Course name is required")
    private String name;

    @NotNull(message = "Course code is required")
    private String code;

    @NotNull(message = "Course info is required")
    private String info;

    @Positive(message = "Credit hours must be positive")
    @NotNull(message = "Credit hours is required")
    @Max(value = 4, message = "Credit hours do not exceed 4 hours")
    private Integer creditHours;

    @NotNull(message = "Semester is required")
    private Semester semester;

    @NotNull(message = "Mandatory is required")
    private Boolean mandatory;

    @NotNull(message = "Department code is required")
    private Set<String> departmentCode;

}
