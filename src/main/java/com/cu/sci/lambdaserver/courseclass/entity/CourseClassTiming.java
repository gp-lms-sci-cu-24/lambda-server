package com.cu.sci.lambdaserver.courseclass.entity;

import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.utils.enums.ClassType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.DayOfWeek;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course_class_timing")
public class CourseClassTiming {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timing_seq")
    @SequenceGenerator(name = "timing_seq", sequenceName = "timing_seq", allocationSize = 10)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "class_type", nullable = false, updatable = false)
    private ClassType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private DayOfWeek day;

    @Column(name = "start_time", nullable = false)
    private Time startTime;

    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_class_id", updatable = false)
    private CourseClass courseClass;

//    @OneToMany(mappedBy = "courseClassTiming")
//    Collection<TimingRegister> courseClassTimings;
}
