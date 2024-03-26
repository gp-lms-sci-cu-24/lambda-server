package com.cu.sci.lambdaserver.courseclasstiming;

import com.cu.sci.lambdaserver.courseClass.CourseClass;
import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.utils.enums.ClassType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courseclasstiming")
public class CourseClassTiming {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timing_seq")
    @SequenceGenerator(name = "timing_seq", sequenceName = "timing_seq", allocationSize = 10)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private CourseClass courseclass;

    @Enumerated(EnumType.STRING)
    @Column(name = "class_type")
    private ClassType type;

    @Column(name = "day")
    private String day;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;

    @ManyToOne
    private Location location;
}
