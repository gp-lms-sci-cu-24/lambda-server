package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return studentRepository.findById(id).map(exsistStudent->{
            Optional.ofNullable(studentDetails.getGpa()).ifPresent(exsistStudent::setGpa);
            Optional.ofNullable(studentDetails.getCreditHours()).ifPresent(exsistStudent::setCreditHours);
            Optional.ofNullable(studentDetails.getLevel()).ifPresent(exsistStudent::setLevel);
            return studentRepository.save(exsistStudent) ;
        }).orElseThrow(()->new EntityNotFoundException("Department with ID " + id + " does not exist")) ;
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }


}
