package com.cu.sci.lambdaserver.StudentPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository ;

    public List<Student> getAllStudents(){
        return studentRepository.findAll() ;
    }

    public Optional<Student> getStudent(Long id){
        return studentRepository.findById(id) ;
    }

    public Student createStudent(Student student){
        return studentRepository.save(student) ;
    }

    public Student updateStudent(Student studentDetails , Long id){
        Student student = studentRepository.findById(id).orElseThrow(()->new RuntimeException("Student Not Found With This Id" + id)) ;
        student.setLevel(studentDetails.getLevel());
        student.setCreditHours(studentDetails.getCreditHours());
        student.setDepartment(studentDetails.getDepartment());
        return studentRepository.save(student) ;
    }

    public void deleteStudent (Student studentDetails , Long id){
        Student student = studentRepository.findById(id).orElseThrow(()->new RuntimeException("Student Not Found With This Id" + id)) ;
        studentRepository.delete(student);

    }
}
