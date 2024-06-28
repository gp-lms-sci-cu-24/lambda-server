package com.cu.sci.lambdaserver.student;

import com.cu.sci.lambdaserver.student.dto.*;
import com.cu.sci.lambdaserver.student.service.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final IStudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto createStudent(@RequestBody @Valid CreateStudentRequestDto studentDto) {
        return studentService.creatStudent(studentDto);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public Collection<StudentDto> createStudents(@RequestBody @Valid CreateStudentsDto createStudentsDto) {
        return studentService.createStudents(createStudentsDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<StudentDto> getAllStudents(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return studentService.getAllStudents(pageNo, pageSize);
    }

    @GetMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDto getStudent(@PathVariable String code) {
        return studentService.getStudent(code);
    }

    @GetMapping(path = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Set<StudentDto> search(@RequestParam String q) {
        return studentService.search("%" + q + "%");
    }

    @PutMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDto updateStudent(@PathVariable String code, @RequestBody @Valid UpdateStudentDto studentDto) {
        return studentService.updateStudent(code, studentDto);
    }

    @PutMapping(path = "/bulk")
    @ResponseStatus(HttpStatus.OK)
    public Collection<StudentDto> updateStudents(@RequestBody @Valid UpdateStudentsDto updateStudentsDto) {
        return studentService.updateStudents(updateStudentsDto);
    }

    @DeleteMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable String code) {
        studentService.deleteStudent(code);
    }

}
