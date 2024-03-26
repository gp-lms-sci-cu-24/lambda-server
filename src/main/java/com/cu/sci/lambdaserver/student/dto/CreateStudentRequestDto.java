package com.cu.sci.lambdaserver.student.dto;

import com.cu.sci.lambdaserver.department.DepartmentDto;
import com.cu.sci.lambdaserver.utils.enums.Level;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record CreateStudentRequestDto(

        @NotNull(message = "user name cannot be null")
        String username,
        @NotNull(message = "Password cannot be null")
        String password,

        @NotNull(message = "first name cannot be null")
        String firstName,

        @NotNull(message = "father name cannot be null")
        String fatherName,

        @NotNull(message = "grand father name cannot be null")
        String grandfatherName,

        @NotNull(message = "last name cannot be null")
        String lastname,

        @NotNull(message = "code cannot be null")
        @Size(max = 7, message = "The student code must not exceed 7 numbers")
        String code,

        @PositiveOrZero(message = "credithours must be positive")

        Integer creditHours,

        @NotNull(message = "address cannot be null")
        String address,

        Double gpa,

        Level level,

        String joiningYear,

        String department_code
) {
}
