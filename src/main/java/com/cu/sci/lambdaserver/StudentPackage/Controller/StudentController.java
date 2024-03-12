package com.cu.sci.lambdaserver.StudentPackage.Controller;

import com.cu.sci.lambdaserver.StudentPackage.Dto.StudentDto;
import com.cu.sci.lambdaserver.StudentPackage.Entities.Student;
import com.cu.sci.lambdaserver.StudentPackage.Mapper.Mapper;
import com.cu.sci.lambdaserver.StudentPackage.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("students")
public class StudentController {
    private final Mapper<Student, StudentDto> studentMapper;
    @Autowired
    private StudentService studentService;

    public StudentController(Mapper<Student, StudentDto> studentMapper) {
        this.studentMapper = studentMapper;
    }

    @PostMapping()
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        Student studentEntity = studentMapper.mapFrom(studentDto);
        Student savedStudent = studentService.creatStudent(studentEntity);
        return new ResponseEntity(studentMapper.mapTo(savedStudent), HttpStatus.CREATED);
    }

    @GetMapping
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return students.stream().map(studentMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id) {
        Optional<Student> foundstudent = studentService.getStudent(id);
        return foundstudent.map(student -> new ResponseEntity<>(studentMapper.mapTo(student), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id,@RequestBody StudentDto studentDto){
        if(!studentService.isExsist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }
        Student student = studentMapper.mapFrom(studentDto) ;
        Student updatedStudent = studentService.updateStudent(id,student) ;
        return new ResponseEntity<>(studentMapper.mapTo(updatedStudent),HttpStatus.OK) ;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletStudent(@PathVariable("id") Long id){
        if(!studentService.isExsist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }
}
