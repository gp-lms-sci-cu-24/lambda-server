package com.cu.sci.lambdaserver.professor;

import com.cu.sci.lambdaserver.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "professors")
public class Professor extends User {
    private String professorId;
    private String departmentId;
    private String major;
}
