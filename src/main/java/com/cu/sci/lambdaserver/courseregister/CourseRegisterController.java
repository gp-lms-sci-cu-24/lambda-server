package com.cu.sci.lambdaserver.courseregister;

import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.courseregister.service.ICourseRegisterService;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "v1/api/course-register")
@RequiredArgsConstructor
public class CourseRegisterController {
    private final ICourseRegisterService courseRegisterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseRegisterOutDto createCourseRegister(@Validated(CourseRegisterInDto.CreateValidation.class) @RequestBody CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterService.createCourseRegister(courseRegisterInDto);
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseRegisterOutDto studentCreateCourseRegister(@Validated(CourseRegisterInDto.StudentCreateValidation.class) @RequestBody CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterService.studentCreateCourseRegister(courseRegisterInDto);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseRegisterOutDto> getAllCourseRegisters(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return courseRegisterService.getAllCourseRegisters(pageNo, pageSize);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CourseRegisterOutDto> studentGetAllCourseRegisters() {
        return courseRegisterService
                .studentGetAllCourseRegisters();
    }
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseRegisterOutDto getCourseRegister(@PathVariable Long id) {
        return courseRegisterService.getCourseRegister(id);
    }

    @GetMapping(path = "/student/{code}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CourseRegisterOutDto> getAllStudentCourseRegisters(@PathVariable String code) {
        return courseRegisterService
                .getStudentRegisteredCourses(code);
    }

    @GetMapping(path = "/class/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<StudentDto> getAllCourseClassStudents(@PathVariable Long id) {
        return courseRegisterService
                .getAllCourseClassStudents(id);
    }
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CourseRegisterOutDto updateCourseRegister(@Validated(CourseRegisterInDto.UpdateValidation.class) @RequestBody CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterService.updateCourseRegister(courseRegisterInDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CourseRegisterOutDto deleteCourseRegister(@PathVariable("id") Long id) {
        return courseRegisterService.deleteCourseRegister(id);
    }
}