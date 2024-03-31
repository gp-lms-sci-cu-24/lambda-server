package com.cu.sci.lambdaserver.professor;

import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "professors")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long professorId;

    @OneToOne
    @JoinColumn(name = "id")
    private User user;

//    @OneToMany
//    @JoinColumn(name = "id")
//    private List<Department> department;
}
