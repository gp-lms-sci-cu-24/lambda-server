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
public class CourseClassDto {

    @NotNull(groups = {CreateCourseClassDto.UpdateValidation.class}, message = "Course Class ID cannot be null during update")
    private Long courseClassId;

    @NotNull(groups = {CreateCourseClassDto.CreateValidation.class}, message = "Course Semester cannot be null during creation")
    @Null(groups = {CreateCourseClassDto.UpdateValidation.class}, message = "Course Semester must be null during update")
    private String courseSemester;

    @Null(groups = {CreateCourseClassDto.UpdateValidation.class, CreateCourseClassDto.CreateValidation.class}, message = "Course State cannot be set by dto")
    private String courseState;

    @NotNull(groups = {CreateCourseClassDto.CreateValidation.class}, message = "Max Capacity cannot be null during creation")
    private Integer maxCapacity;

    private String  courseCode;

    private Integer groupNumber;

}
