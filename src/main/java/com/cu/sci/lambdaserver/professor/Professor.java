package com.cu.sci.lambdaserver.professor;

import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}