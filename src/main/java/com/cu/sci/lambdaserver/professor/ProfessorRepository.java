package com.cu.sci.lambdaserver.professor;

import com.cu.sci.lambdaserver.user.UserAbstractionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends UserAbstractionRepository<Professor> {
}
