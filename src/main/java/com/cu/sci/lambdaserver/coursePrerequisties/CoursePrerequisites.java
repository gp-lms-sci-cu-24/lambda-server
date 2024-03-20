package com.cu.sci.lambdaserver.coursePrerequisties;

import com.cu.sci.lambdaserver.course.Course;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courseprerequisites")
public class CoursePrerequisites {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prerequisites_seq")
    @SequenceGenerator(name = "prerequisites_seq", sequenceName = "prerequisites_seq", allocationSize = 10)
    private Long id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Course prerequisite;

}
