package com.cu.sci.lambdaserver.timingregister;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
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
@Table(name = "timing_registers")
public class TimingRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private CourseClass courseClass;

    @ManyToOne
    private CourseClassTiming courseClassTiming;

    @CreationTimestamp
    private LocalDateTime registerDate;

}
