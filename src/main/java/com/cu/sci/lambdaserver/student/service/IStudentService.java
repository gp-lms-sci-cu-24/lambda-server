package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.dto.CreateStudentRequestDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IStudentService {
    StudentDto creatStudent(CreateStudentRequestDto student);

    Page<Student> getAllStudents(Integer pageNo, Integer pageSize);

    Optional<Student> getStudent(Long id);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
