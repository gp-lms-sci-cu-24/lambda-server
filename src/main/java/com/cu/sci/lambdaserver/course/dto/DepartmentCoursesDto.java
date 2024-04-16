package com.cu.sci.lambdaserver.course.dto;


import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.entites.DepartmentCoursesKey;
import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import jakarta.persistence.*;
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

    private Semester semester;

    private Boolean mandatory;

}
