package com.cu.sci.lambdaserver.course.entites;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(name = "course_seq", sequenceName = "course_seq", allocationSize = 10)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String info;

    @Column(nullable = false)
    private Integer creditHours;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<DepartmentCourses> departmentCoursesSet;

    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<Course> coursePrerequisites;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Course course = (Course) o;
        return getId() != null && Objects.equals(getId(), course.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
    //[
    //    {
    //        "id": 1,
    //        "name": "number 2",
    //        "code": "comp494",
    //        "info": "computer science",
    //        "creditHours": 3
    //    },
    //    {
    //        "id": 2,
    //        "name": "number 1",
    //        "code": "comp493",
    //        "info": "computer science",
    //        "creditHours": 3
    //    },
    //    {
    //        "id": 12,
    //        "name": "number 3",
    //        "code": "comp495",
    //        "info": "computer science",
    //        "creditHours": 3
    //    },
    //    {
    //        "id": 13,
    //        "name": "number 4",
    //        "code": "comp496",
    //        "info": "computer science",
    //        "creditHours": 3
    //    },
    //    {
    //        "id": 14,
    //        "name": "number 5",
    //        "code": "comp497",
    //        "info": "computer science",
    //        "creditHours": 3
    //    }
    //]
}
