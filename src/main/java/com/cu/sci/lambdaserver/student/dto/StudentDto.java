package com.cu.sci.lambdaserver.student.dto;


import com.cu.sci.lambdaserver.department.DepartmentDto;
import com.cu.sci.lambdaserver.utils.enums.Level;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Long id ;

    @NotNull(message = "user name cannot be null")
    private String userName;

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
    private Integer creditHours;

    @NotNull(message = "address cannot be null")
    private String address;

    private Double gpa;

    private Level level;

    private String joiningYear;

    private DepartmentDto department ;
}
