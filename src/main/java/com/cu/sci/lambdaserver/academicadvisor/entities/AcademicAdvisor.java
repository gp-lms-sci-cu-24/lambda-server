package com.cu.sci.lambdaserver.academicadvisor.entities;

import com.cu.sci.lambdaserver.academicadvisor.AdvisorType;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "academic_advisor")
public class AcademicAdvisor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "advisor_id", nullable = false)
    private Professor advisor;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdvisorType type;
}
