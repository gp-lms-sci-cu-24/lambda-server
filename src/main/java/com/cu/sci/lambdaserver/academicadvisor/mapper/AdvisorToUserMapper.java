package com.cu.sci.lambdaserver.academicadvisor.mapper;

import com.cu.sci.lambdaserver.academicadvisor.AdvisorType;
import com.cu.sci.lambdaserver.academicadvisor.entities.AcademicAdvisor;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.ProfessorRepository;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.dto.UserDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdvisorToUserMapper implements IMapper<AcademicAdvisor, UserDto> {
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    private final IMapper<User, UserDto> userMapper;
    private final IMapper<Student, StudentDto> studentMapper;
    private final IMapper<Professor, ProfessorDto> professorMapper;


    @Override
    public UserDto mapTo(AcademicAdvisor academicAdvisor) {
        User user = academicAdvisor.getUser();
        AdvisorType type = academicAdvisor.getType();

        if (type.equals(AdvisorType.STUDENT_ADVISOR)) {
            Optional<Student> student = studentRepository.findById(user.getId());
            if (student.isPresent()) {
                return studentMapper.mapTo(student.get());
            }
        } else if (type.equals(AdvisorType.PROFESSOR_SUPERVISOR)) {
            Optional<Professor> professor = professorRepository.findById(user.getId());
            if (professor.isPresent()) {
                return professorMapper.mapTo(professor.get());
            }
        }
        return userMapper.mapTo(user);
    }

    @Override
    public AcademicAdvisor mapFrom(UserDto userDto) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AcademicAdvisor update(UserDto userDto, AcademicAdvisor academicAdvisor) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
