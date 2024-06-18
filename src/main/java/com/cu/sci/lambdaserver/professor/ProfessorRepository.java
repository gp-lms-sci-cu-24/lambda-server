package com.cu.sci.lambdaserver.professor;

import com.cu.sci.lambdaserver.user.UserAbstractionRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends UserAbstractionRepository<Professor> {
    Optional<Professor> findByUsername(String username);

    boolean existsByUsername(String username);

    Collection<Professor> findAllByFirstNameIgnoreCase(String name);
}
