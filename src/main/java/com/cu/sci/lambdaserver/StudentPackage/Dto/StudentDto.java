package com.cu.sci.lambdaserver.StudentPackage.Dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class StudentDto {
    private Long id;
    private String firstName ;
    private String lastName ;
    private Long code ;
    private Integer creditHours  ;
    private String address ;
    private Integer phoneNumber ;
    private  String highSchoolmajor ;
    private Double gpa   ;
    private Integer level ;
    private String department ;

}
