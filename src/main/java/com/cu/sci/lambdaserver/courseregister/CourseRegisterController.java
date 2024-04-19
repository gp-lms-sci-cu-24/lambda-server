package com.cu.sci.lambdaserver.courseregister;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.courseclass.CourseClass;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.courseregister.dto.GetStudentRegisteredCoursesDto;
import com.cu.sci.lambdaserver.courseregister.mapper.CourseRegisterOutDtoMapper;
import com.cu.sci.lambdaserver.courseregister.service.CourseRegisterService;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import com.cu.sci.lambdaserver.utils.enums.State;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
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
    private final CourseRegisterService courseRegisterService;
    private final CourseRegisterOutDtoMapper courseRegisterOutDtoMapper;
    private final iMapper<CourseClass, CourseClassDto> classCourseClassDtoiMapper;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseRegisterOutDto createCourseRegister(@Validated(CourseRegisterInDto.CreateValidation.class)  @RequestBody CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterOutDtoMapper.mapTo(courseRegisterService.createCourseRegister(courseRegisterInDto) );
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseRegisterOutDto> getAllCourseRegisters(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Page<CourseRegister> page = courseRegisterService.getAllCourseRegisters(pageNo, pageSize);
        Page<CourseRegisterOutDto> dtoPage = page.map(courseRegisterOutDtoMapper::mapTo);
        return dtoPage;
    }
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseRegisterOutDto getCourseRegister(@PathVariable Long id) {
        return courseRegisterOutDtoMapper.mapTo(courseRegisterService.getCourseRegister(id) );
    }
    @GetMapping(path = "/student/{code}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CourseClassDto> getAllStudentRegisteredCourses(@PathVariable String code) {
        return courseRegisterService
            .findStudentRegisteredCourses(code).stream()
            .map(classCourseClassDtoiMapper::mapTo).collect(Collectors.toList() );
    }
    @GetMapping(path = "/student/{code}/{state}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CourseClassDto> getAllStudentRegisteredCoursesBySemester(@PathVariable String code, @PathVariable State state) {
        return courseRegisterService
                .findStudentRegisteredCoursesBySemester(code,state).stream()
                .map(classCourseClassDtoiMapper::mapTo).collect(Collectors.toList() );
    }
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CourseRegisterOutDto updateCourseRegister(@Validated(CourseRegisterInDto.UpdateValidation.class) @RequestBody CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterOutDtoMapper.mapTo(courseRegisterService.updateCourseRegister(courseRegisterInDto) );
    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CourseRegisterOutDto deleteCourseRegister(@PathVariable("id") Long id) {
        return courseRegisterOutDtoMapper.mapTo(courseRegisterService.deleteCourseRegister(id) );
    }
}