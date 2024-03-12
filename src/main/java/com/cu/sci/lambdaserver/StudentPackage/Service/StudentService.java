package com.cu.sci.lambdaserver.StudentPackage.Service;

import com.cu.sci.lambdaserver.StudentPackage.Entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student creatStudent(Student student);

    List<Student> getAllStudents();

    Optional<Student> getStudent(Long id);

    Boolean isExsist(Long id);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
