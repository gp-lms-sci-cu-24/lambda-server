package com.cu.sci.lambdaserver.courseclass.dto;

import com.cu.sci.lambdaserver.course.entites.Course;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseClassResponse {


    private Long courseClassId;

    private String courseSemester;

    private String courseState;

    private Integer maxCapacity;

    private String courseCode;

    private String courseName;

    private Integer groupNumber;

    private String year;


}
