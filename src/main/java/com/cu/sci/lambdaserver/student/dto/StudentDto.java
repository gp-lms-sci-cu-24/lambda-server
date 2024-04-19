package com.cu.sci.lambdaserver.student.dto;


import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.utils.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private String firstName;

    private String fatherName;

    private String grandfatherName;

    private String lastname;

    private String code;

    private Integer creditHours;

    private String address;

    private Double gpa;

    private Level level;

    private String joiningYear;

    private DepartmentDto department;
}
