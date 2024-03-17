package com.cu.sci.lambdaserver.course;


import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="department_courses")
public class DepartmentCourses {

    @EmbeddedId
    private DepartmentCoursesKey departmentCoursesKey ;

    @ManyToOne
    @MapsId("departmentId")
    @JoinColumn(name = "department_id")
    private Department department ;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course ;

    @Enumerated(EnumType.STRING)
    private Semester semester ;

    @Column(nullable = false)
    private Boolean mandatory ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentCourses that = (DepartmentCourses) o;
        return Objects.equals(departmentCoursesKey, that.departmentCoursesKey) && Objects.equals(department, that.department) && Objects.equals(course, that.course) && semester == that.semester && Objects.equals(mandatory, that.mandatory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentCoursesKey, department, course, semester, mandatory);
    }
}
