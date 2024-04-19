package com.cu.sci.lambdaserver.professor.service;

import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IProfessorService {
    Professor createProfessor(Professor professor);

    Page<Professor> getAllProfessors(Integer pageNo, Integer pageSize);

    ProfessorDto getProfessor(Long id);

    Professor updateProfessor(Long id, Professor professor);

    void deleteProfessor(Long id);
}