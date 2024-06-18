package com.cu.sci.lambdaserver.professor.service;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.courseregister.service.CourseRegisterService;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.ProfessorRepository;
import com.cu.sci.lambdaserver.professor.dto.CreateProfessorRequestDto;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.professor.mapper.ProfessorMapper;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorService implements IProfessorService {

    private final IAuthenticationFacade authenticationFacade;
    private final ProfessorRepository professorRepository;
    private final CourseRegisterService courseRegisterService;
    private final ProfessorMapper professorMapper;
    private final CourseClassRepository courseClassRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProfessorDto createProfessor(CreateProfessorRequestDto professorDto) {
        boolean existByUsername = userRepository.existsByUsernameIgnoreCase(professorDto.getUsername());
        if (existByUsername) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There already user with this username");
        }


        Professor professor = Professor.builder()
                .gender(professorDto.getGender())
                .email(professorDto.getEmail())
                .degree(professorDto.getDegree())
                .firstName(professorDto.getFirstName())
                .lastName(professorDto.getLastName())
                .username(professorDto.getUsername())
                .password(passwordEncoder.encode(professorDto.getPassword()))
                .build();
        // save it
        professorRepository.save(professor);

        return professorMapper.mapTo(professor);
    }

    @Override
    public Page<Professor> getAllProfessors(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Professor> prof = professorRepository.findAll(pageable);
        if (prof.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Professors found");
        return prof;
    }

    @Override
    public Collection<Professor> getAllProfessorsByFirstName(String name) {
        Collection<Professor> prof = professorRepository.findAllByFirstNameIgnoreCase(name);
        if (prof.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Professors found");
        return prof;
    }

    @Override
    public ProfessorDto getProfessor(String username) {
        Optional<Professor> professor = professorRepository.findByUsername(username);
        if (professor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found");
        }
        return professorMapper.mapTo(professor.get());
    }

    @Override
    public ProfessorDto updateProfessor(String username, Professor professorDetails) {
        return professorMapper.mapTo(professorRepository.findByUsername(username).map(professorRepository::save).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor with username " + username + " does not exist")));
    }

    @Override
    public void deleteProfessor(String username) {
        if (!professorRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor with username " + username + " does not exist");
        }
        professorRepository.existsByUsername(username);
    }

    @Override
    public List<CourseClass> getCourseClasses(String username) {
        return professorRepository.findByUsername(username).map(Professor::getCourseClasses)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor with username " + username + " does not exist"));
    }

    @Override
    public Professor assignCourseClass(String username, Long courseClassId) {
        Professor professor = professorRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor with username " + username + " does not exist"));
        CourseClass courseClass = courseClassRepository.findById(courseClassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CourseClass with ID " + courseClassId + " does not exist"));
        if (professor.getCourseClasses().contains(courseClass)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CourseClass with ID " + courseClassId + " already exists in professor with ID " + username);
        }
        professor.getCourseClasses().add(courseClass);
        return professorRepository.save(professor);
    }

    @Override
    public Professor removeCourseClass(String username, Long courseClassId) {
        Professor professor = professorRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor with ID " + username + " does not exist"));
        CourseClass courseClass = courseClassRepository.findById(courseClassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CourseClass with ID " + courseClassId + " does not exist"));
        if (!professor.getCourseClasses().contains(courseClass)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CourseClass with ID " + courseClassId + " does not belong to professor with ID " + username);
        }
        professor.getCourseClasses().remove(courseClass);
        return professorRepository.save(professor);
    }

    @Override
    public Collection<StudentDto> getMyStudentsInCourseClass(Long courseClassId) {
        User user = authenticationFacade.getAuthenticatedUser();
        Professor professor = professorRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found."));
        CourseClass courseClass = courseClassRepository.findById(courseClassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CourseClass with ID " + courseClassId + " does not exist"));
        if (!professor.getCourseClasses().contains(courseClass)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CourseClass with ID " + courseClassId + " does not belong to professor with ID " + user.getId());
        }
        return courseRegisterService.getAllCourseClassStudents(courseClassId);
    }

    @Override
    public Collection<StudentDto> getMyStudents() {
        User user = authenticationFacade.getAuthenticatedUser();
        Professor professor = professorRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found."));
        List<StudentDto> students = new ArrayList<>();
        for (CourseClass courseClass : professor.getCourseClasses()) {
            students.addAll(courseRegisterService.getAllCourseClassStudents(courseClass.getId()));
        }
        return students;
    }
}
