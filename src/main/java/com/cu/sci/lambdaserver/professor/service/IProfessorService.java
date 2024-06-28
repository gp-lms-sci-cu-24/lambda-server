package com.cu.sci.lambdaserver.professor.service;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.dto.CreateProfessorRequestDto;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IProfessorService {
    @PreAuthorize("hasRole('ADMIN')")
    ProfessorDto createProfessor(CreateProfessorRequestDto professorDto);

    @PreAuthorize("hasRole('ADMIN')")
    Page<Professor> getAllProfessors(Integer pageNo, Integer pageSize);

    @PreAuthorize("hasRole('ADMIN')")
    Collection<Professor> getAllProfessorsByFirstName(String name);

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    ProfessorDto getProfessor(String username);

    Set<ProfessorDto> search(String q) ;

    @PreAuthorize("hasRole('ADMIN')")
    ProfessorDto updateProfessor(String username, Professor professor);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteProfessor(String username);

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    List<CourseClassDto> getCourseClasses(String username);

    @PreAuthorize("hasRole('ADMIN')")
    Professor assignCourseClass(String username, Long courseClassId);

    @PreAuthorize("hasRole('ADMIN')")
    Professor removeCourseClass(String username, Long courseClassId);

//    @PreAuthorize("hasRole('PROFESSOR')")
//    Collection<StudentDto> getMyStudents();

//    @PreAuthorize("hasRole('PROFESSOR')")
//    Collection<StudentDto> getMyStudentsInCourseClass(Long courseClassId);
}