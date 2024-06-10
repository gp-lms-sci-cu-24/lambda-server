package com.cu.sci.lambdaserver.course.dto;


import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.utils.enums.DepartmentSemester;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCoursesDto {

    private DepartmentDto department;

    private DepartmentSemester semester;

    private Boolean mandatory;

}
