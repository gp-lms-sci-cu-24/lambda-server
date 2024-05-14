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
public class CreateCourseClassDto {

    @NotNull(groups = {UpdateValidation.class}, message = "Course Class ID cannot be null during update")
    private Long courseClassId;

    @NotNull(groups = {CreateValidation.class}, message = "Course code cannot be null during creation")
    private String cousreCode;

    @NotNull(groups = {CreateValidation.class}, message = "Course Semester cannot be null during creation")
    @Null(groups = {UpdateValidation.class}, message = "Course Semester must be null during update")
    private Semester courseSemester;

    @NotNull(groups = {CreateValidation.class}, message = "Max Capacity cannot be null during creation")
    @Null(groups = {UpdateValidation.class}, message = "Max Capacity must be null during update")
    private Integer maxCapacity;

    public interface UpdateValidation {
    }


    public interface CreateValidation {
    }

}
