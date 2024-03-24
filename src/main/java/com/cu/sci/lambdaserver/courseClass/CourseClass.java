package com.cu.sci.lambdaserver.courseClass;

import com.cu.sci.lambdaserver.course.Course;
import com.cu.sci.lambdaserver.utils.enums.State;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course_classes")
public class CourseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseClassId;
    @ManyToOne
    @JoinColumn(name = "id")
    private Course course;
    @CreationTimestamp
    private LocalDateTime publishDate;
    @Enumerated(EnumType.STRING)
    private Semester courseSemester;
    @Enumerated(EnumType.STRING)
    private State courseState;
    private Integer maxCapacity;

    @Builder.Default
    private Integer numberOfStudentsRegistered = 0;
    private Integer capacitySoFar;
    @Builder.Default
    @Column(name = "group_number_seq")
    private Integer groupNumber = 0;

// later refer to admin and professor entities
//    private String adminId;
//    private String professorId;
}
