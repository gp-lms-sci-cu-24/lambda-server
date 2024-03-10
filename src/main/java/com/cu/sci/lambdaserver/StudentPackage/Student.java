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
    private String firstName ;
    @NotNull()
    private String lastName ;
    @NotNull()
    @Column(unique = true)
    private Long code ;
    private Integer creditHours = 0 ;
    @NotNull()
    private String address ;
    @NotNull()
    private Integer phoneNumber ;
    @NotNull()
    private  String highSchoolmajor ;
    private Double gpa  = 0.0 ;
    @NotNull()
    private Integer level ;
    @NotNull()
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Integer getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(Integer creditHours) {
        this.creditHours = creditHours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHighSchoolmajor() {
        return highSchoolmajor;
    }

    public void setHighSchoolmajor(String highSchoolmajor) {
        this.highSchoolmajor = highSchoolmajor;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
