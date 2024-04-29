package com.cu.sci.lambdaserver.professor.service;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.dto.CreateProfessorRequestDto;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProfessorService {
    ProfessorDto createProfessor(CreateProfessorRequestDto professorDto);

    Page<Professor> getAllProfessors(Integer pageNo, Integer pageSize);

    ProfessorDto getProfessor(Long id);

    Professor updateProfessor(Long id, Professor professor);

    void deleteProfessor(Long id);

    List<CourseClass> getCourseClasses(Long id);

    Professor addCourseClass(Long id, Long courseClassId);
}