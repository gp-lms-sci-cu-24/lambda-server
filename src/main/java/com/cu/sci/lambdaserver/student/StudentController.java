package com.cu.sci.lambdaserver.student;

import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import com.cu.sci.lambdaserver.student.service.iStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "v1/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final iMapper<Student, StudentDto> studentMapper;
    private final iStudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        Student studentEntity = studentMapper.mapFrom(studentDto);
        Student savedStudent = studentService.creatStudent(studentEntity);
        return new ResponseEntity(studentMapper.mapTo(savedStudent), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getAllStudents(@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        Page<Student> page = studentService.getAllStudents(pageNo,pageSize) ;
        Page<StudentDto> dtoPage = page.map(studentMapper::mapTo) ;
        return new ResponseEntity<>(dtoPage, HttpStatus.OK) ;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id) {
        Optional<Student> foundstudent = studentService.getStudent(id);
        return foundstudent.map(student -> new ResponseEntity<>(studentMapper.mapTo(student), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        if (!studentService.isExsist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Student student = studentMapper.mapFrom(studentDto);
        Student updatedStudent = studentService.updateStudent(id, student);
        return new ResponseEntity<>(studentMapper.mapTo(updatedStudent), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletStudent(@PathVariable("id") Long id) {
        if (!studentService.isExsist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
