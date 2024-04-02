package com.cu.sci.lambdaserver.classGroup;

import com.cu.sci.lambdaserver.courseclass.CourseClass;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course_class_groups")
public class CourseClassGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classGroupId;
    @ManyToOne
    @JoinColumn(name = "course_class_id")
    private CourseClass courseClass;
    private Integer maxCapacity;
    @Builder.Default
    private Integer numberOfStudentsRegistered = 0;
    private Integer groupNumber;

//    later refer to class timing
//    private String courseClassTimingId;
}
