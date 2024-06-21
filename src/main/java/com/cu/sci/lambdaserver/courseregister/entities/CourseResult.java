package com.cu.sci.lambdaserver.courseregister.entities;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.utils.entities.DateAudit;
import com.cu.sci.lambdaserver.utils.enums.CourseResultState;
import com.cu.sci.lambdaserver.utils.enums.Rate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "course_result")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResult extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_result_seq")
    @SequenceGenerator(name = "course_result_seq", sequenceName = "course_result_seq", allocationSize = 10)
    private Long id;

    @Column(columnDefinition = "integer default 0")
    @Builder.Default
    private Integer grade = 0;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_class_id", updatable = false, nullable = false)
    private CourseClass courseClass;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) default 'FAIL'")
    @Builder.Default
    private Rate rate = Rate.FAIL;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) default 'FAILED'")
    @Builder.Default
    private CourseResultState state = CourseResultState.FAILED;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", updatable = false, nullable = false)
    private Student student;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CourseResult that = (CourseResult) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
