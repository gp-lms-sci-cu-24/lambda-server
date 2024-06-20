package com.cu.sci.lambdaserver.course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    @NotBlank(message = "name is mandatory")
    @NotNull(message = "name cannot be null")
    private String name;

    @NotBlank(message = "code is mandatory")
    @NotNull(message = "code cannot be null")
    private String code;

    private String info;

    private String image;

    @Min(value = 1, message = "creditHours must be greater than 0")
    @NotNull(message = "creditHours cannot be null")
    @NotBlank(message = "creditHours is mandatory")
    @Positive(message = "creditHours must be positive")
    private Integer creditHours;

    private Collection<DepartmentCoursesDto> departments;

    private Collection<String> coursePrerequisites;

}
