package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.dto.CreateStudentRequestDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.UserRepository;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService implements IStudentService {
    private final UserRepository userRepository;

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final iMapper<Student, StudentDto> studentMapper;
    private final iMapper<Student, CreateStudentRequestDto> createStudentRequestDtoMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public StudentDto creatStudent(CreateStudentRequestDto studentDto) {
        // check department
        Optional<Department> department = departmentRepository
                .findDepartmentByCodeIgnoreCase(studentDto.getDepartmentCode());
        if(department.isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT , "department not found with this code") ;
        }

        Optional<Student> findedStudentByCode = studentRepository.findByCode(studentDto.getCode());
        if(findedStudentByCode.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT , "Code is already exist.") ;
        }


        Student student = createStudentRequestDtoMapper.mapFrom(studentDto);

        student.setDepartment(department.get());
        student.setUsername(studentDto.getCode());
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));

        log.info("Student: {}",student);
        Student saveStudent = studentRepository.save(student) ;

        return studentMapper.mapTo(saveStudent) ;
//        return studentMapper.mapTo(student) ;
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
