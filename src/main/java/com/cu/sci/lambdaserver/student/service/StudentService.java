package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.dto.CreateStudentRequestDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;
    private final iMapper<Student, StudentDto> studentMapper;
    private final iMapper<Student, CreateStudentRequestDto> createStudentRequestDtoMapper;

    @Override
    public StudentDto creatStudent(CreateStudentRequestDto studentDto) {


        Student student = createStudentRequestDtoMapper.mapFrom(studentDto);
        Student saveStudent = studentRepository.save(student) ;

        return studentMapper.mapTo(saveStudent) ;
    }

    @Override
    public Page<Student> getAllStudents(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return studentRepository.findAll(pageable);
    }

    @Override
    public Optional<Student> getStudent(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id).map(exsistStudent -> {
            Optional.ofNullable(studentDetails.getGpa()).ifPresent(exsistStudent::setGpa);
            Optional.ofNullable(studentDetails.getCreditHours()).ifPresent(exsistStudent::setCreditHours);
            Optional.ofNullable(studentDetails.getLevel()).ifPresent(exsistStudent::setLevel);
            return studentRepository.save(exsistStudent);
        }).orElseThrow(() -> new EntityNotFoundException("Student with ID " + id + " does not exist"));
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student with ID " + id + " does not exist");
        }
        studentRepository.deleteById(id);
    }
}
