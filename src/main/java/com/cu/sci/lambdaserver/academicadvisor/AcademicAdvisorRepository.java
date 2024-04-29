package com.cu.sci.lambdaserver.academicadvisor;

import com.cu.sci.lambdaserver.academicadvisor.entities.AcademicAdvisor;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.Optional;

public interface AcademicAdvisorRepository extends JpaRepository<AcademicAdvisor, Long>, PagingAndSortingRepository<AcademicAdvisor, Long> {
    Optional<AcademicAdvisor> findByUser(User user);

    Optional<AcademicAdvisor> findByAdvisorAndUser(Professor professor, User user);

    Page<AcademicAdvisor> findByAdvisorAndType(Professor professor, AdvisorType type, Pageable pageable);

    Collection<AcademicAdvisor> findByAdvisorAndType(Professor professor, AdvisorType type);

    Page<AcademicAdvisor> findByAdvisor(Professor advisor, Pageable pageable);

    Collection<AcademicAdvisor> findByAdvisor(Professor advisor);
}