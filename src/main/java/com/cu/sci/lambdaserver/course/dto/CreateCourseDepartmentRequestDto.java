package com.cu.sci.lambdaserver.course.dto;

import com.cu.sci.lambdaserver.utils.enums.DepartmentSemester;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateCourseDepartmentRequestDto(
        @NotNull(message = "departmentCode is required")
        String departmentCode,
        @NotNull(message = "mandatory is required")
        boolean mandatory,
        @NotNull(message = "semester is required")
        DepartmentSemester semester
) {

}
