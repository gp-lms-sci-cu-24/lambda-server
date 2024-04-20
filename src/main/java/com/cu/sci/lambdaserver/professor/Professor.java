package com.cu.sci.lambdaserver.professor;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "professors")
public class Professor extends User {

    public Professor() {
        setRoles(List.of(Role.PROFESSOR));
    }

    public static Professor.ProfessorBuilder<?, ?> builder() {
        return new Professor.ProfessorBuilderImpl().roles(List.of(Role.PROFESSOR));
    }

    @ManyToMany
    @JoinTable(
            name = "course_classes_professors",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "course_classes_id")
    )
    private List<CourseClass> CourseClasses;
}