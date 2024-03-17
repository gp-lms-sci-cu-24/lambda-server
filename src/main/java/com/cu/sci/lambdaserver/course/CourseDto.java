package com.cu.sci.lambdaserver.course;

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
    private String name;

    private String code;

    private String info;

    private Integer creditHours;

}
