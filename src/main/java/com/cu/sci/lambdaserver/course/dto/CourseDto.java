package com.cu.sci.lambdaserver.course.dto;

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
public class CourseDto {

    private Long id;

    @NotBlank(message = "code is mandatory")
    private String name;

    @NotBlank(message = "code is mandatory")
    private String code;

    private String info;

    @Min(value = -1, message = "creditHours should be non negative")
    @Max(value = 7, message = "creditHours should be less than 7")
    private Integer creditHours;

//    private Set<DepartmentCoursesDto> departmentCoursesSet;

}
