package com.cu.sci.lambdaserver.professor.service;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.ProfessorRepository;
import com.cu.sci.lambdaserver.professor.dto.CreateProfessorRequestDto;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.professor.mapper.ProfessorMapper;
import com.cu.sci.lambdaserver.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorService implements IProfessorService {

    private final ProfessorRepository professorRepository;
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
        return professorRepository.findAll(pageable);
    }

    @Override
    public ProfessorDto getProfessor(Long id) {
        Optional<Professor> professor = professorRepository.findById(id);
        if (professor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found");
        }
        return professorMapper.mapTo(professor.get());
    }

    @Override
    public Professor updateProfessor(Long id, Professor professorDetails) {
        return professorRepository.findById(id).map(existingProfessor -> {
//            existingProfessor.getUser().setId(professorDetails.getUser().getId() );
            return professorRepository.save(existingProfessor);
        }).orElseThrow(() -> new EntityNotFoundException("Location with ID " + id + " does not exist"));
    }

    @Override
    public void deleteProfessor(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new EntityNotFoundException("Location with ID " + id + " does not exist");
        }
        professorRepository.deleteById(id);
    }

    @Override
    public List<CourseClass> getCourseClasses(Long id) {
        return professorRepository.findById(id).map(Professor::getCourseClasses)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with ID " + id + " does not exist"));
    }

    @Override
    public Professor addCourseClass(Long id, Long courseClassId) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor with ID " + id + " does not exist"));
        CourseClass courseClass = courseClassRepository.findById(courseClassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CourseClass with ID " + courseClassId + " does not exist"));
        professor.getCourseClasses().add(courseClass);
        return professorRepository.save(professor);
    }
}
