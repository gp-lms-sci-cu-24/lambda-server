package com.cu.sci.lambdaserver.courseregister.dto;

import com.cu.sci.lambdaserver.utils.enums.Rate;
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
    public interface UpdateValidation {}
    public interface CreateValidation {}
    public interface NoValidation {}

    @NotNull(message = "Course register ID is required", groups = {UpdateValidation.class} )
    private Long courseRegisterId;

    @NotNull(message = "Course class ID is required", groups = {CreateValidation.class} )
    @Null(message = "Course class ID must be null during update", groups = {UpdateValidation.class} )
    private Long courseClassId;

//    @NotNull(message = "Student ID is required", groups = {CreateValidation.class} )
//    @Null(message = "Student ID must be null during update", groups = {UpdateValidation.class})
//    private Long studentId;

    @NotNull(message = "Student ID is required", groups = {CreateValidation.class} )
    @Null(message = "Student ID must be null during update", groups = {UpdateValidation.class})
    private String studentCode;

    @Min(value = 0, message = "Grade must be a positive number", groups = {CreateValidation.class, UpdateValidation.class} )
    private Long grade;

    private Rate courseRate;
}
