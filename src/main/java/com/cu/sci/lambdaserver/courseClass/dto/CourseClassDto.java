package com.cu.sci.lambdaserver.courseClass.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseClassDto {
    private Long courseClassId;
    private Long courseId;
    private LocalDate publishDate;
    private String courseSemester;
    private String courseState;
    private Integer maxCapacity;

    private Integer numberOfStudentsRegistered = 0;
    private Integer capacitySoFar = 0;
    private Integer groupNumberSeq = 0;

    // Getters and setters
    // You can also add constructors and other methods as needed
}
