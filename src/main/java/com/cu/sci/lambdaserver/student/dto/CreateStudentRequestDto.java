package com.cu.sci.lambdaserver.student.dto;

import com.cu.sci.lambdaserver.department.DepartmentDto;
import com.cu.sci.lambdaserver.utils.enums.Level;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequestDto{
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
        @Size(max = 7 , message = "The student code must not exceed 7 numbers")
        private String code;

        @PositiveOrZero(message = "credithours must be positive")
        private Integer creditHours=0;

        @NotNull(message = "address cannot be null")
        private String address;

        private Double gpa=0.0;

        private Level level=Level.LEVEL_1;

        @JsonProperty("joining_year")
        private String joiningYear;

        @JsonProperty("department_code")
        private String departmentCode;
}
