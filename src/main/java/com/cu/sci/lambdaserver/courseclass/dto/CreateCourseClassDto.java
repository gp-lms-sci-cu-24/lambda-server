package com.cu.sci.lambdaserver.courseclass.dto;

import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseClassDto {

    @NotNull(groups = {CreateValidation.class}, message = "Course code cannot be null during creation")
    @JsonProperty("course_code")
    private String courseCode;

    @NotNull(groups = {CreateValidation.class}, message = "Course Semester cannot be null during creation")
    private YearSemester courseSemester;

    @NotNull(groups = {CreateValidation.class}, message = "Max Capacity cannot be null during creation")
    private Integer maxCapacity;

    public interface UpdateValidation {
    }


    public interface CreateValidation {
    }

}
