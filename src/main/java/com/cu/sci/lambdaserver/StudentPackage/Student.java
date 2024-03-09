package com.cu.sci.lambdaserver.StudentPackage;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull()
    @NotEmpty()
    private String firstName ;
    @NotNull()
    @NotEmpty()
    private String lastName ;
    @NotNull()
    @NotEmpty()
    @Column(unique = true)
    private Long code ;
    private Integer creditHours = 0 ;
    @NotNull()
    @NotEmpty()
    private String address ;
    @NotNull()
    @NotEmpty()
    private Integer phoneNumber ;
    @NotNull()
    @NotEmpty()
    private  String highSchoolmajor ;
    private Double gpa  = 0.0 ;
    @NotNull()
    @NotEmpty()
    private Integer level ;
    @NotNull()
    @NotEmpty()
    private String department ;
    @ManyToMany
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>() ;


    public Student() {
    }

    public Student(String firstName, String lastName, Long code, Integer creditHours, String address, Integer phoneNumber, String highSchoolmajor, Double gpa, Integer level, String department, Set<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.code = code;
        this.creditHours = creditHours;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.highSchoolmajor = highSchoolmajor;
        this.gpa = gpa;
        this.level = level;
        this.department = department;
        this.courses = courses;
    }


    public Student(String firstName, String lastName, Long code, Integer creditHours, String address, Integer phoneNumber, String highSchoolmajor, Double gpa, Integer level, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.code = code;
        this.creditHours = creditHours;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.highSchoolmajor = highSchoolmajor;
        this.gpa = gpa;
        this.level = level;
        this.department = department;
    }
}
