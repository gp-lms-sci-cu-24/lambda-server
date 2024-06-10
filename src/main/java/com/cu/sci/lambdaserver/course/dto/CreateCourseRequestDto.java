package com.cu.sci.lambdaserver.course.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseRequestDto {

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

    @NotNull(message = "At least one department is required")
    @Size(min = 1, message = "At least one department is required")
    @Valid
    private Collection<CreateCourseDepartmentRequestDto> departments;

    private Collection<String> coursePrerequisites;
}
