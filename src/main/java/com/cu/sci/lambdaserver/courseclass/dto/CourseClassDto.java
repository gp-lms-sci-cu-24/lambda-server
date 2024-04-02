package com.cu.sci.lambdaserver.courseclass.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseClassDto {
    private Long courseClassId;
    private Long courseId;
    private LocalDateTime publishDate;
    private String courseSemester;
    private String courseState;
    private Integer maxCapacity;

    private Integer numberOfStudentsRegistered;
    private Integer capacitySoFar;
    private Integer groupNumber;

    // Getters and setters
    // You can also add constructors and other methods as needed
}
