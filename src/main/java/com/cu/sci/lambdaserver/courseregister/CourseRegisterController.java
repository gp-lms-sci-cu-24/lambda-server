package com.cu.sci.lambdaserver.courseregister;

import com.cu.sci.lambdaserver.courseclass.service.ICourseClassService;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.courseregister.mapper.CourseRegisterOutDtoMapper;
import com.cu.sci.lambdaserver.courseregister.service.CourseRegisterService;
import com.cu.sci.lambdaserver.courseregister.service.ICourseRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "v1/api/course-register")
@RequiredArgsConstructor
public class CourseRegisterController {
    private final ICourseRegisterService courseRegisterService;
    private final CourseRegisterOutDtoMapper courseRegisterOutDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseRegisterOutDto createCourseRegister(@Validated(CourseRegisterInDto.CreateValidation.class) @RequestBody CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterOutDtoMapper.mapTo(courseRegisterService.createCourseRegister(courseRegisterInDto));
    }
    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseRegisterOutDto studentCreateCourseRegister(@Validated(CourseRegisterInDto.StudentCreateValidation.class) @RequestBody CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterOutDtoMapper
            .mapTo(courseRegisterService.studentCreateCourseRegister(courseRegisterInDto) );
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseRegisterOutDto> getAllCourseRegisters(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Page<CourseRegister> page = courseRegisterService.getAllCourseRegisters(pageNo, pageSize);
        return page.map(courseRegisterOutDtoMapper::mapTo);
    }
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CourseRegisterOutDto> studentGetAllCourseRegisters() {
        return courseRegisterService
                .studentGetAllCourseRegisters().stream()
                .map(courseRegisterOutDtoMapper::mapTo).collect(Collectors.toList() );
    }
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseRegisterOutDto getCourseRegister(@PathVariable Long id) {
        return courseRegisterOutDtoMapper.mapTo(courseRegisterService.getCourseRegister(id));
    }

    @GetMapping(path = "/student/{code}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CourseRegisterOutDto> getAllStudentCourseRegisters(@PathVariable String code) {
        return courseRegisterService
                .getStudentRegisteredCourses(code).stream()
                .map(courseRegisterOutDtoMapper::mapTo).collect(Collectors.toList());
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CourseRegisterOutDto updateCourseRegister(@Validated(CourseRegisterInDto.UpdateValidation.class) @RequestBody CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterOutDtoMapper.mapTo(courseRegisterService.updateCourseRegister(courseRegisterInDto));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CourseRegisterOutDto deleteCourseRegister(@PathVariable("id") Long id) {
        return courseRegisterOutDtoMapper.mapTo(courseRegisterService.deleteCourseRegister(id));
    }
}