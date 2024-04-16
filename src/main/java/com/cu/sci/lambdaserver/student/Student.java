package com.cu.sci.lambdaserver.student;

import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.enums.Level;
import com.cu.sci.lambdaserver.utils.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "students")
public class Student extends User {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String fatherName;

    @Column(nullable = false)
    private String grandfatherName;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(columnDefinition = "integer default 0")
    private Integer creditHours;

    private String address;

    @Column(columnDefinition = "double default 0")
    private Double gpa;

    @Enumerated(EnumType.STRING)
    private Level level;

    private String joiningYear;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Student() {
        setRoles(List.of(Role.STUDENT));
    }

    public static StudentBuilder<?, ?> builder() {
        return new StudentBuilderImpl().roles(List.of(Role.STUDENT));
    }
}
