package com.cu.sci.lambdaserver.courseclass.dto;

import com.cu.sci.lambdaserver.utils.enums.ClassType;
import com.cu.sci.lambdaserver.utils.enums.CourseClassState;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Set;

public record CreateCourseClassRequestDto(
        @NotNull(message = "`courseCode` is required")
        String courseCode,
        @NotNull(message = "`semester` is required")
        YearSemester semester,
        @NotNull(message = "`adminProfessor` is required")
        String adminProfessor,
        @NotNull(message = "`maxCapacity` is required")
        @Min(value = 0, message = "`maxCapacity` must be greater than or equal 0")
        Integer maxCapacity,
        Integer year,
        CourseClassState state,

        @Size(min = 1, message = "`timings` must have at least one element")
        @NotNull(message = "`timings` is required")
        @Valid
        Set<TimingRequestDto> timings
) {

    public record TimingRequestDto(
            @NotNull(message = "`day` is required")
            DayOfWeek day,
            @NotNull(message = "`startTime` is required")
            Time startTime,
            @NotNull(message = "`endTime` is required")
            Time endTime,
            @NotNull(message = "`locationId` is required")
            Long locationId,
            @NotNull(message = "`type` is required")
            ClassType type
    ) {
    }
}
