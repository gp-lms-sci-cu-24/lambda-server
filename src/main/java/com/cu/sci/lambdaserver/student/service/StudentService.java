package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.student.dto.CreateStudentRequestDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.student.dto.UpdateStudentDto;
import com.cu.sci.lambdaserver.user.UserRepository;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService implements IStudentService {
    private final UserRepository userRepository;

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final iMapper<Student, UpdateStudentDto> studentMapper;
    private final iMapper<Student, CreateStudentRequestDto> createStudentRequestDtoMapper;
    private final iMapper<Student, StudentDto> studentDtoiMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentDto creatStudent(CreateStudentRequestDto studentDto) throws ResponseStatusException {
        // check department
        Optional<Department> department = departmentRepository
                .findDepartmentByCodeIgnoreCase(studentDto.getDepartmentCode());
        if (department.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "department not found with this code");
        }

        Optional<Student> findedStudentByCode = studentRepository.findByCode(studentDto.getCode());
        if (findedStudentByCode.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Code is already exist.");
        }


        Student student = createStudentRequestDtoMapper.mapFrom(studentDto);

        student.setDepartment(department.get());
        student.setUsername(studentDto.getCode());
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));

        log.info("Student: {}", student);
        Student saveStudent = studentRepository.save(student);

        return studentDtoiMapper.mapTo(saveStudent);
    }

    @Override
    public Page<StudentDto> getAllStudents(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Student> students = studentRepository.findAll(pageable) ;
        //check if list empty
        if (students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return students.map(studentDtoiMapper::mapTo);
    }

    @Override
    public StudentDto getStudent(String code) {
        Optional<Student> student = studentRepository.findByCode(code);
        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " student not found with this code ");
        }
        return studentDtoiMapper.mapTo(student.get());
    }

    @Override
    public StudentDto updateStudent(String code, UpdateStudentDto studentDetails) {
        //check if user exist
        Optional<Student> student = studentRepository.findByCode(code);

        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " student not found with this code ");
        }

        //update student
        student.map(foundStudent -> {
            Optional.ofNullable(studentDetails.getGpa()).ifPresent(foundStudent::setGpa);
            Optional.ofNullable(studentDetails.getCreditHours()).ifPresent(foundStudent::setCreditHours);
            Optional.ofNullable(studentDetails.getLevel()).ifPresent(foundStudent::setLevel);
            return studentRepository.save(foundStudent);
        });
        return studentDtoiMapper.mapTo(student.get());
    }

    @Override
    public void deleteStudent(String code) {
        //check if student exist
        Optional<Student> student = studentRepository.findByCode(code);
        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " student not found with this code ");
        }
        studentRepository.delete(student.get());
    }

}
