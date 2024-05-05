package com.cu.sci.lambdaserver.courseclass.dto;

import com.cu.sci.lambdaserver.utils.enums.Semester;
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
public class CourseClassInDto {
    @NotNull(groups = {UpdateValidation.class}, message = "Course Class ID cannot be null during update")
    private Long courseClassId;
    @NotNull(groups = {CreateValidation.class}, message = "Course ID cannot be null during creation")
    private Long courseId;
    @NotNull(groups = {CreateValidation.class}, message = "Course Semester cannot be null during creation")
    @Null(groups = {UpdateValidation.class}, message = "Course Semester must be null during update")
    private Semester courseSemester;
    @NotNull(groups = {CreateValidation.class}, message = "Max Capacity cannot be null during creation")
    @Null(groups = {UpdateValidation.class}, message = "Max Capacity must be null during update")
    private Integer maxCapacity;

    public interface UpdateValidation {
    }

//    @Null(groups = {UpdateValidation.class, CreateValidation.class}, message = "Course Semester cannot be set by dto")
//    private String courseState;

    public interface CreateValidation {
    }

//    @Null(groups = {CreateValidation.class, UpdateValidation.class}, message = "Number of Students must be null during creation and update")
//    private Integer numberOfStudentsRegistered;
//
//    @Null(groups = {CreateValidation.class, UpdateValidation.class}, message = "Group Number must be null during creation and update")
//    private Integer groupNumber;
}
