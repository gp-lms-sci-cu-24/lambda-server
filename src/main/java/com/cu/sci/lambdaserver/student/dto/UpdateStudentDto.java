package com.cu.sci.lambdaserver.student.dto;


import com.cu.sci.lambdaserver.utils.enums.Level;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentDto {

    // need it when making batch updates
    private String code;
    @PositiveOrZero(message = "credit hours must be positive")
    @Max(value = 146, message = "credit hours do not exceed 146 hours")
    private Integer creditHours = 0;

    @PositiveOrZero(message = "gpa must be positive")
    @Max(value = 4, message = "gpa do not exceed 4.0")
    private Double gpa = 0.0;

    private Level level = Level.LEVEL_1;

}
