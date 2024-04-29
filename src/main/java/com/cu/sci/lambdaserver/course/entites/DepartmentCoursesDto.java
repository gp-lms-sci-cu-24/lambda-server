package com.cu.sci.lambdaserver.course.entites;

import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.utils.enums.Semester;
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
    //    private CourseDto course;
    private Semester semester;
    private Boolean mandatory;
}
