package com.cu.sci.lambdaserver.courseclass.dto;

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
    @NotNull(groups = {CourseClassInDto.UpdateValidation.class}, message = "Course Class ID cannot be null during update")
    private Long courseClassId;
    @NotNull(groups = {CourseClassInDto.CreateValidation.class}, message = "Course ID cannot be null during creation")
    private Long courseId;
    @NotNull(groups = {CourseClassInDto.CreateValidation.class}, message = "Course Semester cannot be null during creation")
    @Null(groups = {CourseClassInDto.UpdateValidation.class}, message = "Course Semester must be null during update")
    private String courseSemester;
    @Null(groups = {CourseClassInDto.UpdateValidation.class, CourseClassInDto.CreateValidation.class}, message = "Course Semester cannot be set by dto")
    private String courseState;
    @NotNull(groups = {CourseClassInDto.CreateValidation.class}, message = "Max Capacity cannot be null during creation")
//    @Null(groups = {CourseClassInDto.UpdateValidation.class}, message = "Max Capacity must be null during update")
    private Integer maxCapacity;


//    @Null(groups = {CourseClassInDto.CreateValidation.class, CourseClassInDto.UpdateValidation.class}, message = "Number of Students must be null during creation and update")
//    private Integer numberOfStudentsRegistered;

//    @Null(groups = {CourseClassInDto.CreateValidation.class, CourseClassInDto.UpdateValidation.class}, message = "Group Number must be null during creation and update")
//    private Integer groupNumber;

    // Getters and setters
    // You can also add constructors and other methods as needed
}
