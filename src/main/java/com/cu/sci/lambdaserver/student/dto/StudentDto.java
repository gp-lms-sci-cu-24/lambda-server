package com.cu.sci.lambdaserver.student.dto;


import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.user.dto.UserDto;
import com.cu.sci.lambdaserver.utils.enums.Gender;
import com.cu.sci.lambdaserver.utils.enums.Level;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto extends UserDto {

    private String firstName;

    private String fatherName;

    private String grandfatherName;

    private String lastname;

    private Integer creditHours;

    private String address;

    private Double gpa;

    private Level level;

    private Integer creditHoursSemester;

    private Gender gender;

    private String joiningYear;

    private DepartmentDto department;

}
