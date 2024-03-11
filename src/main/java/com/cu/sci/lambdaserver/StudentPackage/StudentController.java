package com.cu.sci.lambdaserver.StudentPackage;

import com.cu.sci.lambdaserver.StudentPackage.Dto.StudentDto;
import com.cu.sci.lambdaserver.StudentPackage.Entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentController {
    @Autowired
    private StudentService studentService ;
    @GetMapping()
    public ResponseEntity<List<StudentDto>> getAllStudents(){
        List<StudentDto> students = studentService.getAllStudents();
        if(students.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById (@PathVariable long id ){
        StudentDto studentDto = studentService.getStudent(id) ;
        if(studentDto!=null){
            return new ResponseEntity<>(studentDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }
    }

    @PostMapping("create-student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student createdStudent= studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student studentDetails , @PathVariable Long id){
        try{
            Student updatedStudent = studentService.updateStudent(studentDetails, id);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent (@PathVariable Long id){
        try{
            studentService.deleteStudent(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
