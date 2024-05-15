package com.cu.sci.lambdaserver.courseregister.dto;

import com.cu.sci.lambdaserver.utils.enums.CourseRegisterState;
import com.cu.sci.lambdaserver.utils.enums.Rate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
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
public class CourseRegisterInDto {


    @NotNull(message = "Course class ID is required", groups = {CreateValidation.class, StudentCreateValidation.class})
    @Null(message = "Course class ID must be null during update or student create", groups = {UpdateValidation.class})
    @JsonProperty("course_code")
    private String courseCode;

    @NotNull(message = "Group number is required", groups = {CreateValidation.class})
    private  Integer groupNumber;

    @NotNull(message = "Student ID is required", groups = {CreateValidation.class})
    @Null(message = "Student ID must be null during update", groups = {UpdateValidation.class})
    private String studentCode;

    @NotNull(message = "Rate is required", groups = {UpdateValidation.class})
    private Long grade;


    public interface UpdateValidation {
    }

    public interface CreateValidation {
    }

    public interface StudentCreateValidation {
    }
}
