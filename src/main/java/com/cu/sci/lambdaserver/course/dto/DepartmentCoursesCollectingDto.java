package com.cu.sci.lambdaserver.course.dto;

import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCoursesCollectingDto {

    private String name;

    private String code;

    private String info;

    private Integer creditHours;

    private YearSemester semester;

    private Boolean mandatory;

    private Collection<String> departmentCode;

}
