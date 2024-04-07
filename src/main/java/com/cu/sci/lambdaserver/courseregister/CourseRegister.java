package com.cu.sci.lambdaserver.courseregister;

import com.cu.sci.lambdaserver.courseclass.CourseClass;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.utils.enums.Rate;
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
@Table(name = "course_registers")
public class CourseRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long courseRegisterId;

    @ManyToOne
    @JoinColumn(name = "course_class_id")
    private CourseClass courseClass;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @CreationTimestamp
    private LocalDateTime registerDate;
    @Builder.Default
    private Long grade = -1L;
}
