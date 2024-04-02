package com.cu.sci.lambdaserver.student;

import com.cu.sci.lambdaserver.student.dto.CreateStudentRequestDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.student.dto.UpdateStudentDto;
import com.cu.sci.lambdaserver.student.service.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDto updateStudent(@PathVariable String code, @RequestBody @Valid UpdateStudentDto studentDto) {
        return studentService.updateStudent(code, studentDto);
    }

    @DeleteMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletStudent(@PathVariable String code) {
        studentService.deleteStudent(code);
    }

}
