package com.cu.sci.lambdaserver.StudentPackage.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull()
    private String firstName;
    @NotNull()
    private String lastName;
    @NotNull()
    @Column(unique = true)
    private Long code;
    private Integer creditHours = 0;
    @NotNull()
    private String address;
    @NotNull()
    private Integer phoneNumber;
    @NotNull()
    private String highSchoolmajor;
    private Double gpa = 0.0;
    @NotNull()
    private Integer level;
    @NotNull()
    private String department;
//    @ManyToMany
//    @JoinTable(
//            name = "student_courses",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id")
//    )
//
//    private Set<Course> courses = new HashSet<>() ;
}
