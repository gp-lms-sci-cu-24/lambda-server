package com.cu.sci.lambdaserver.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashBoardDto {

    private Long totalCourses;
    private Long totalStudents;
    private Long totalProfessors;
    private Long totalLocations;
    private Long totalDepartments;
    private Long totalCourseClasses;
}
