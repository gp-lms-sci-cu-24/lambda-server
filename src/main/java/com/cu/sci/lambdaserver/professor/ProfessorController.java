package com.cu.sci.lambdaserver.professor;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.professor.dto.CreateProfessorRequestDto;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.professor.service.IProfessorService;
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

    @GetMapping(path = "/search/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Professor> getAllProfessorsByFirstName(@PathVariable("name") String name) {
        return professorService.getAllProfessorsByFirstName(name);
    }

    @GetMapping(path = "/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ProfessorDto getProfessor(@PathVariable("username") String username) {
        return professorService.getProfessor(username);
    }

    @PutMapping(path = "/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ProfessorDto updateProfessor(@PathVariable("username") String username, @Valid @RequestBody Professor professor) {
        return professorService.updateProfessor(username, professor);
    }

    @DeleteMapping(path = "/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProfessor(@PathVariable("username") String username) {
        professorService.deleteProfessor(username);
    }

    @GetMapping(path = "/{username}/course-classes")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseClassDto> getCourseClasses(@PathVariable String username) {
        return professorService.getCourseClasses(username).stream().map(courseClassMapper::mapTo).collect(Collectors.toList());
    }

    @PostMapping(path = "/{username}/course-classes/{courseClassId}")
    @ResponseStatus(HttpStatus.OK)
    public Professor addCourseClass(@PathVariable String username, @PathVariable Long courseClassId) {
        return professorService.assignCourseClass(username, courseClassId);
    }

    @DeleteMapping(path = "/{username}/course-classes/{courseClassId}")
    @ResponseStatus(HttpStatus.OK)
    public Professor removeCourseClass(@PathVariable String username, @PathVariable Long courseClassId) {
        return professorService.removeCourseClass(username, courseClassId);
    }

//    @GetMapping(path = "/my-students")
//    @ResponseStatus(HttpStatus.OK)
//    public Collection<StudentDto> getMyStudents() {
//        return professorService.getMyStudents();
//    }
//
//    @GetMapping(path = "/my-students/{courseClassId}")
//    @ResponseStatus(HttpStatus.OK)
//    public Collection<StudentDto> getMyStudentsInCourseClass(@PathVariable Long courseClassId) {
//        return professorService.getMyStudentsInCourseClass(courseClassId);
//    }

}