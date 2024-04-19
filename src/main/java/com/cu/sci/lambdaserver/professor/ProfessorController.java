package com.cu.sci.lambdaserver.professor;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.professor.mapper.ProfessorMapper;
import com.cu.sci.lambdaserver.professor.service.IProfessorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "v1/api/professor")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorMapper professorMapper;
    private final CourseClassMapper courseClassMapper;
    private final IProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorDto> createProfessor(@Validated(ProfessorDto.CreateValidation.class) @RequestBody ProfessorDto professorDto) {
        Professor professorEntity = professorMapper.mapFrom(professorDto);
        Professor savedProfessor = professorService.createProfessor(professorEntity);
        return new ResponseEntity<>(professorMapper.mapTo(savedProfessor), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProfessorDto>> getAllProfessors(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Page<Professor> page = professorService.getAllProfessors(pageNo, pageSize);
        Page<ProfessorDto> dtoPage = page.map(professorMapper::mapTo);
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProfessorDto> getProfessor(@PathVariable Long id) {
        Optional<Professor> foundLocation = professorService.getProfessor(id);
        return foundLocation.map(location -> new ResponseEntity<>(professorMapper.mapTo(location), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping
    public ResponseEntity<ProfessorDto> updateProfessor(@Validated(ProfessorDto.UpdateValidation.class) @RequestBody ProfessorDto professorDto) {
        try {
            Professor professor = professorMapper.mapFrom(professorDto);
            Professor updatedProfessor = professorService.updateProfessor(professorDto.getProfessorId(), professor);
            return new ResponseEntity<>(professorMapper.mapTo(updatedProfessor), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteProfessor(@PathVariable("id") Long id) {
        try {
            professorService.deleteProfessor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(path = "/{id}/course-classes")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseClassDto> getCourseClasses(@PathVariable Long id) {
        return professorService.getCourseClasses(id).stream().map(courseClassMapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping(path = "/{id}/course-classes/{courseClassId}")
    @ResponseStatus(HttpStatus.OK)
    public Professor addCourseClass(@PathVariable Long id, @PathVariable Long courseClassId) {
        return professorService.addCourseClass(id, courseClassId);
    }
}