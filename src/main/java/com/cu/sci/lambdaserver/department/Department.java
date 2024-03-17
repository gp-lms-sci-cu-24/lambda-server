package com.cu.sci.lambdaserver.department;


import com.cu.sci.lambdaserver.course.DepartmentCourses;
import com.cu.sci.lambdaserver.student.Student;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq")
    @SequenceGenerator(name = "department_seq", sequenceName = "department_seq", allocationSize = 10)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String info;

    private String image;

    @OneToMany(mappedBy = "department")
    private Set<DepartmentCourses> departmentCoursesSet ;

    @OneToMany(mappedBy = "department")
    private List<Student> students;


}
