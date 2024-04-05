package com.cu.sci.lambdaserver.professor;

import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "professors")
public class Professor extends User{
    private Long professorId;
    public Professor() {
        setRoles(List.of(Role.TEACHER));
    }

    public static Professor.ProfessorBuilder<?, ?> builder() {
        return new Professor.ProfessorBuilderImpl().roles(List.of(Role.TEACHER));
    }
//    @OneToMany
//    @JoinColumn(name = "id")
//    private List<Department> department;
}