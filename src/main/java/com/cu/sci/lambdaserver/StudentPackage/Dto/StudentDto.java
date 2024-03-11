package com.cu.sci.lambdaserver.StudentPackage.Dto;


import com.cu.sci.lambdaserver.StudentPackage.Entities.Student;
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

    public static StudentDto toDto(Student student){
        return StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .code(student.getCode())
                .creditHours(student.getCreditHours())
                .address(student.getAddress())
                .phoneNumber(student.getPhoneNumber())
                .gpa(student.getGpa())
                .level(student.getLevel())
                .department(student.getDepartment()).build();
    }
}
