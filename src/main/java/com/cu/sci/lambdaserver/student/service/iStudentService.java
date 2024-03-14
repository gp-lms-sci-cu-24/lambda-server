package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.student.Student;

import java.util.List;
import java.util.Optional;

public interface iStudentService {
    Student creatStudent(Student student);

    List<Student> getAllStudents();

    Optional<Student> getStudent(Long id);

    Boolean isExsist(Long id);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
