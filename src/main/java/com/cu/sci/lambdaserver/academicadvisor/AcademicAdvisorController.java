package com.cu.sci.lambdaserver.academicadvisor;

import com.cu.sci.lambdaserver.academicadvisor.service.IAcademicAdvisorService;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.user.dto.UserDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.dto.UsernameRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/academic-advisor")
@RequiredArgsConstructor
@Slf4j
public class AcademicAdvisorController {

    private final IAcademicAdvisorService academicAdvisorService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAcademicAdvisors(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "50") Integer pageSize) {
        return academicAdvisorService.getAllAcademicAdvisors(pageNo, pageSize);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse assignAcademicAdvisorRole(@Valid @RequestBody UsernameRequestDto dto) {
        return academicAdvisorService.assignAcademicAdvisorRole(dto);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse removeAcademicAdvisorRole(@Valid @RequestBody UsernameRequestDto dto) {
        return academicAdvisorService.removeAcademicAdvisorRole(dto);
    }

    @PostMapping("/{professorUsername}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse assignUser(@PathVariable String professorUsername, @Valid @RequestBody UsernameRequestDto dto) {
        return academicAdvisorService.assignUser(professorUsername, dto);
    }

    @DeleteMapping("/{professorUsername}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse removeUser(@PathVariable String professorUsername, @Valid @RequestBody UsernameRequestDto dto) {
        return academicAdvisorService.removeUser(professorUsername, dto);
    }

    @GetMapping("/{professorUsername}")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAssignedUsers(@PathVariable String professorUsername, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "50") Integer pageSize) {
        return academicAdvisorService.getAssignedUsers(professorUsername, pageNo, pageSize);
    }

    @GetMapping("/{professorUsername}/professors")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAssignedProfessors(@PathVariable String professorUsername, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "50") Integer pageSize) {
        return academicAdvisorService.getAssignedUsersByType(professorUsername, AdvisorType.PROFESSOR_SUPERVISOR, pageNo, pageSize);
    }

    @GetMapping("/{professorUsername}/students")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAssignedStudents(@PathVariable String professorUsername, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "50") Integer pageSize) {
        return academicAdvisorService.getAssignedUsersByType(professorUsername, AdvisorType.STUDENT_ADVISOR, pageNo, pageSize);
    }

    @GetMapping("/{professorUsername}/students/all")
    @ResponseStatus(HttpStatus.OK)
    public Collection<StudentDto> getAssignedAllStudents(@PathVariable String professorUsername, @RequestParam(defaultValue = "20") Integer nestingLevel) {
        return academicAdvisorService.getNestedAssignedStudents(professorUsername, nestingLevel);
    }

    @GetMapping("my")
    @ResponseStatus(HttpStatus.OK)
    public ProfessorDto getMyAcademicAdvisor() {
        return academicAdvisorService.getMyAcademicAdvisor();
    }

}
