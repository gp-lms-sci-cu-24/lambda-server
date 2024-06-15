package com.cu.sci.lambdaserver.course.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    @NotBlank(message = "name is mandatory")
    @NotNull(message = "name cannot be null")
    private String name;

    @NotBlank(message = "code is mandatory")
    @NotNull(message = "code cannot be null")
    private String code;

    private String info;

    private String image;

    @Min(value = 0, message = "creditHours should be non negative")
    @Max(value = 7, message = "creditHours should be less than 7")
    private Integer creditHours;

    private Collection<DepartmentCoursesDto> departments;

    private Collection<String> coursePrerequisites;
}
