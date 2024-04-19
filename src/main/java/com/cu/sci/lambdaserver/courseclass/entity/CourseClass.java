package com.cu.sci.lambdaserver.courseclass.entity;

import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.timingregister.TimingRegister;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import com.cu.sci.lambdaserver.utils.enums.State;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course_classes")
public class CourseClass {
    @OneToMany(mappedBy = "courseClass")
    @ToString.Exclude
    Collection<CourseRegister> courseRegisters;
    @OneToMany(mappedBy = "courseClass")
    @ToString.Exclude
    Collection<TimingRegister> courseClassTimings;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseClassId;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @CreationTimestamp
    private LocalDateTime publishDate;
    @Enumerated(EnumType.STRING)
    private Semester courseSemester;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State courseState = State.INACTIVE;
    private Integer maxCapacity;
    @Builder.Default
    private Integer numberOfStudentsRegistered = 0;
    private Integer capacitySoFar;
    @Builder.Default
    @Column(name = "group_number_seq")
    private Integer groupNumber = -1;
}
