package com.cu.sci.lambdaserver.StudentPackage.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
    private Long code ;
    private Integer creditHours  ;
    private String address ;
    private Integer phoneNumber ;
    private  String highSchoolmajor ;
    private Double gpa   ;
    private Integer level ;
    private String department ;
}
