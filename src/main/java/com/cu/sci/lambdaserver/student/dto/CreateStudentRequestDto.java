package com.cu.sci.lambdaserver.student.dto;

import com.cu.sci.lambdaserver.utils.enums.Gender;
import com.cu.sci.lambdaserver.utils.enums.Level;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequestDto {
    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "first name cannot be null")
    private String firstName;

    @NotNull(message = "father name cannot be null")
    private String fatherName;

    @NotNull(message = "grand father name cannot be null")
    private String grandfatherName;

    @NotNull(message = "last name cannot be null")
    private String lastname;

    @NotNull(message = "code cannot be null")
    @Size(max = 7, min = 7, message = "The student code must not exceed 7 numbers")
    private String code;

    @PositiveOrZero(message = "credit hours must be positive")
    @Max(value = 146, message = "credit hours do not exceed 164 hours")
    @JsonProperty("credit_hours")
    private Integer creditHours = 0;

    @NotNull(message = "address cannot be null")
    private String address;

    @PositiveOrZero(message = "address cannot be null")
    @Max(value = 4, message = "gpa do not exceed 4.0")
    private Double gpa = 0.0;

    private Level level = Level.LEVEL_1;

    @JsonProperty("joining_year")
    private String joiningYear;

    @JsonProperty("department_code")
    private String departmentCode;

    @JsonProperty("credit_hours_semester")
    @PositiveOrZero(message = "credit hours semester must be positive")
    private Integer creditHoursSemester = 0 ;

    @NotNull
    private Gender gender ;

}
