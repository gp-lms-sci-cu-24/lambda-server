package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.student.Student;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface iStudentService {
    Student creatStudent(Student student);

    Page<Student> getAllStudents(Integer pageNo, Integer pageSize);

    Optional<Student> getStudent(Long id);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
