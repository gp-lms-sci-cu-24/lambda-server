package com.cu.sci.lambdaserver.academicadvisor.service;

import com.cu.sci.lambdaserver.academicadvisor.AcademicAdvisorRepository;
import com.cu.sci.lambdaserver.academicadvisor.AdvisorType;
import com.cu.sci.lambdaserver.academicadvisor.entities.AcademicAdvisor;
import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.ProfessorRepository;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.UserRepository;
import com.cu.sci.lambdaserver.user.dto.UserDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.dto.UsernameRequestDto;
import com.cu.sci.lambdaserver.utils.enums.Role;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcademicAdvisorService implements IAcademicAdvisorService {

    private final IAuthenticationFacade authenticationFacade;

    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final AcademicAdvisorRepository academicAdvisorRepository;

    private final IMapper<Professor, ProfessorDto> professorMapper;
    private final IMapper<User, UserDto> userMapper;
    private final IMapper<Student, StudentDto> studentMapper;
    private final IMapper<AcademicAdvisor, UserDto> academicAdvisorUserDtoMapper;

    @Override
    public MessageResponse assignAcademicAdvisorRole(UsernameRequestDto usernameDto) {
        Professor professor = professorRepository.findByUsernameIgnoreCase(usernameDto.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Professor not found."));

        boolean hasRole = professor.getRoles().contains(Role.ACADEMIC_ADVISOR);
        if (hasRole)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Professor already has academic advisor role");

        // add Role to professor (roles aren't immutable)
        professor.getRoles().add(Role.ACADEMIC_ADVISOR);

        professorRepository.save(professor);

        return new MessageResponse("Role added to professor successfully.");
    }

    @Override
    public MessageResponse removeAcademicAdvisorRole(UsernameRequestDto usernameDto) {
        Professor professor = professorRepository.findByUsernameIgnoreCase(usernameDto.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Professor not found."));

        boolean hasRole = professor.getRoles().contains(Role.ACADEMIC_ADVISOR);
        if (!hasRole)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Professor doesnt has academic advisor role");

        // remove Role to professor (roles aren't immutable)
        boolean removed = professor.getRoles().remove(Role.ACADEMIC_ADVISOR);
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't remove role");
        }

        professorRepository.save(professor);

        return new MessageResponse("Role removed from professor successfully.");
    }

    @Override
    public Page<UserDto> getAllAcademicAdvisors(Integer pageNo, Integer pageSize) {
        Pageable pageRequest = PageRequest.of(pageNo, pageSize);

        Page<UserDto> userPage = userRepository.findByRolesContains(Role.ACADEMIC_ADVISOR, pageRequest).map(userMapper::mapTo);

        if (userPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No professor has (Academic advisor) role  found.");
        }

        return userPage;
    }


    @Override
    public MessageResponse assignUser(String professorUsername, UsernameRequestDto usernameDto) {
        if (professorUsername.equalsIgnoreCase(usernameDto.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't assign user to himself.");
        }

        Optional<Professor> professor = professorRepository.findByUsernameIgnoreCase(professorUsername);

        if (professor.isEmpty() || !professor.get().hasRole(Role.ACADEMIC_ADVISOR)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Academic Advisor Not Found");
        }

        User user = userRepository.findByUsernameIgnoreCase(usernameDto.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "User not found"));

        Optional<AcademicAdvisor> assignedBefore = academicAdvisorRepository.findByUser(user);
        if (assignedBefore.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format(
                            "User already has academic advisor with username (%s).",
                            assignedBefore.get().getAdvisor().getUsername()
                    ));
        }


        AdvisorType type;
        if (user.hasRole(Role.STUDENT)) {
            type = AdvisorType.STUDENT_ADVISOR;
        } else if (user.hasRole(Role.ACADEMIC_ADVISOR)) {
            type = AdvisorType.PROFESSOR_SUPERVISOR;
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User isn't academic advisor or student.");
        }

        AcademicAdvisor advisor = AcademicAdvisor.builder()
                .advisor(professor.get())
                .user(user)
                .type(type)
                .build();

        academicAdvisorRepository.save(advisor);

        return new MessageResponse(
                String.format(
                        "successfully assigned:  %s is academic advisor for %s now.",
                        advisor.getAdvisor().getUsername(),
                        advisor.getUser().getUsername()
                ));
    }

    @Override
    public MessageResponse removeUser(String professorUsername, UsernameRequestDto usernameDto) {
        if (professorUsername.equalsIgnoreCase(usernameDto.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't remove user from himself.");
        }

        Optional<Professor> professor = professorRepository.findByUsernameIgnoreCase(professorUsername);

        if (professor.isEmpty() || !professor.get().hasRole(Role.ACADEMIC_ADVISOR)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Academic Advisor Not Found");
        }

        User user = userRepository.findByUsernameIgnoreCase(usernameDto.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Invalid Username to remove."));

        AcademicAdvisor advisor = academicAdvisorRepository
                .findByAdvisorAndUser(professor.get(), user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "User isn't assigned to this professor."));

        academicAdvisorRepository.delete(advisor);

        return new MessageResponse(
                String.format(
                        "successfully removed:  %s isn't academic advisor for %s now.",
                        advisor.getAdvisor().getUsername(),
                        advisor.getUser().getUsername()
                ));
    }

    @Override
    public Page<UserDto> getAssignedUsers(String professorUsername, Integer pageNo, Integer pageSize) {
        Professor professor = professorRepository.findByUsernameIgnoreCase(professorUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found."));

        Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("type"));

        Page<UserDto> userPage = academicAdvisorRepository.findByAdvisor(professor, pageRequest)
                .map(academicAdvisorUserDtoMapper::mapTo);

        if (userPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No assigned users found.");
        }

        return userPage;
    }

    @Override
    public Page<UserDto> getAssignedUsersByType(String professorUsername, AdvisorType type, Integer pageNo, Integer pageSize) {
        Professor professor = professorRepository.findByUsernameIgnoreCase(professorUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found."));

        Pageable pageRequest = PageRequest.of(pageNo, pageSize);

        Page<UserDto> userPage = academicAdvisorRepository.findByAdvisorAndType(professor, type, pageRequest)
                .map(academicAdvisorUserDtoMapper::mapTo);

        if (userPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No assigned users found.");
        }

        return userPage;
    }

    @Override
    public Collection<StudentDto> getNestedAssignedStudents(String professorUsername, Integer nestingLevel) {
        if (nestingLevel < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nesting level can't be negative.");
        }

        Professor professor = professorRepository.findByUsernameIgnoreCase(professorUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found."));

        Set<StudentDto> allStudents = new HashSet<>();
        Set<Professor> searchSpace = new HashSet<>(Set.of(professor));

        for (int i = 0; i <= nestingLevel && !searchSpace.isEmpty(); i++) {
            Set<Professor> newSearchSpace = new HashSet<>();

            for (Professor currentProfessor : searchSpace) {
                // add students
                Set<StudentDto> currentStudents = academicAdvisorRepository.findByAdvisorAndType(currentProfessor, AdvisorType.STUDENT_ADVISOR)
                        .stream()
                        .map(AcademicAdvisor::getUser)
                        .map(user -> studentRepository.findById(user.getId()).orElseThrow())
                        .map(studentMapper::mapTo)
                        .collect(Collectors.toSet());

                allStudents.addAll(currentStudents);


                // add professors to new search space in next level
                Set<Professor> nestedProfessors = academicAdvisorRepository.findByAdvisorAndType(currentProfessor, AdvisorType.PROFESSOR_SUPERVISOR)
                        .stream()
                        .map(AcademicAdvisor::getUser)
                        .map(user -> professorRepository.findById(user.getId()).orElseThrow())
                        .collect(Collectors.toSet());

                newSearchSpace.addAll(nestedProfessors);
            }

            searchSpace = newSearchSpace;
        }

        return allStudents;
    }

    @Override
    public ProfessorDto getMyAcademicAdvisor() {
        User user = authenticationFacade.getAuthenticatedUser();

        AcademicAdvisor academicAdvisor = academicAdvisorRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Academic Advisor not found."));

        return professorMapper.mapTo(academicAdvisor.getAdvisor());
    }

}
