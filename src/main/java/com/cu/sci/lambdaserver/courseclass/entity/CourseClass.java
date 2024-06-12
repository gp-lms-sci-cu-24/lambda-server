package com.cu.sci.lambdaserver.courseclass.entity;

import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.timingregister.TimingRegister;
import com.cu.sci.lambdaserver.utils.entities.DateAudit;
import com.cu.sci.lambdaserver.utils.enums.CourseClassState;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course_classes")
public class CourseClass extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YearSemester semester;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CourseClassState state = CourseClassState.REGISTER;

    @Column(name = "max_capacity", nullable = false, columnDefinition = "integer default 0")
    private Integer maxCapacity;

    @Builder.Default
    @Column(name = "number_of_students_registered", nullable = false, columnDefinition = "integer default 0")
    private Integer numberOfStudentsRegistered = 0;

    @Column(nullable = false, columnDefinition = "integer default 0")
    @Builder.Default
    private Integer year = 0;

    @Column(name = "group_number_seq", nullable = false)
    private Integer groupNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "admin_professor_id", nullable = false)
    private Professor adminProfessor;

    @ManyToMany(mappedBy = "CourseClasses")
    @ToString.Exclude
    private Set<Professor> professors;


    // not reviewed yet
    @OneToMany(mappedBy = "courseClass")
    @ToString.Exclude
    private Collection<CourseRegister> courseRegisters;

    @OneToMany(mappedBy = "courseClass")
    @ToString.Exclude
    private Collection<TimingRegister> courseClassTimings;

}
