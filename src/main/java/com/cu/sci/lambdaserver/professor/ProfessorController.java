package com.cu.sci.lambdaserver.professor;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.professor.dto.CreateProfessorRequestDto;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.professor.mapper.ProfessorMapper;
import com.cu.sci.lambdaserver.professor.service.IProfessorService;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/professors")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorMapper professorMapper;
    private final IProfessorService professorService;
    private final CourseClassMapper courseClassMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfessorDto createProfessor(@Valid @RequestBody CreateProfessorRequestDto professorDto) {
        return professorService.createProfessor(professorDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Professor> getAllProfessors(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return professorService.getAllProfessors(pageNo, pageSize);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfessorDto getProfessor(@PathVariable("id") Long id) {
        return professorService.getProfessor(id);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfessorDto updateProfessor(@PathVariable("id") Long id, @Valid @RequestBody Professor professor) {
        return professorService.updateProfessor(id, professor);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProfessor(@PathVariable("id") Long id) {
        professorService.deleteProfessor(id);
    }

    @GetMapping(path = "/{id}/course-classes")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseClassDto> getCourseClasses(@PathVariable Long id) {
        return professorService.getCourseClasses(id).stream().map(courseClassMapper::mapTo).collect(Collectors.toList());
    }

    @PostMapping(path = "/{id}/course-classes/{courseClassId}")
    @ResponseStatus(HttpStatus.OK)
    public Professor addCourseClass(@PathVariable Long id, @PathVariable Long courseClassId) {
        return professorService.assignCourseClass(id, courseClassId);
    }

    @DeleteMapping(path = "/{id}/course-classes/{courseClassId}")
    @ResponseStatus(HttpStatus.OK)
    public Professor removeCourseClass(@PathVariable Long id, @PathVariable Long courseClassId) {
        return professorService.removeCourseClass(id, courseClassId);
    }

    @GetMapping(path = "/my-students")
    @ResponseStatus(HttpStatus.OK)
    public Collection<StudentDto> getMyStudents() {
        return professorService.getMyStudents();
    }

    @GetMapping(path = "/my-students/{courseClassId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<StudentDto> getMyStudentsInCourseClass(@PathVariable Long courseClassId) {
        return professorService.getMyStudentsInCourseClass(courseClassId);
    }

}