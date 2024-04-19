package com.cu.sci.lambdaserver.course.entites;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import lombok.*;

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
