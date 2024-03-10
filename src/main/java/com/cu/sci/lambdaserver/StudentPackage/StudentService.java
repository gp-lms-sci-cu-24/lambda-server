package com.cu.sci.lambdaserver.StudentPackage;

import com.cu.sci.lambdaserver.StudentPackage.Dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cu.sci.lambdaserver.StudentPackage.Dto.StudentDto.toDto;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository ;

    public List<StudentDto> getAllStudents(){
        List<Student> students = studentRepository.findAll() ;
        return students.stream().map(student -> toDto(student)).collect(Collectors.toList());
    }

    public StudentDto getStudent(Long id){
        Optional<Student> student = studentRepository.findById(id) ;
        if(student.isPresent()){
            return StudentDto.toDto(student.get()) ;
        }else {
            return null ;
        }
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
