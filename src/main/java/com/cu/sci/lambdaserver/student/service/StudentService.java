package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StudentService implements iStudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student creatStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll().stream().toList();
    }

    @Override
    public Optional<Student> getStudent(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Boolean isExsist(Long id) {
        return studentRepository.existsById(id);
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student Not Found With This Id" + id));
        student.setLevel(studentDetails.getLevel());
        student.setCreditHours(studentDetails.getCreditHours());
//        student.setDepartment(studentDetails.getDepartment());
        student.setGpa(studentDetails.getGpa());
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }


}
