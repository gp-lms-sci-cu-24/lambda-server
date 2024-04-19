package com.cu.sci.lambdaserver.professor.service;

import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.ProfessorRepository;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.professor.mapper.ProfessorMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorService implements IProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    @Override
    public Professor createProfessor(Professor professor) {
        return professorRepository.save(professor);
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
}
