package com.cu.sci.lambdaserver.course;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.service.CourseService;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = ("/api/v1/course"))
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final iMapper<Course, CourseDto> courseMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDto> GetCourses() {
        return courseService.getCourses().stream().map(courseMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto getCourseById(@PathVariable(name = "id") Long courseId) {
        return courseMapper.mapTo(courseService.getCourse(courseId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto createCourse(@Valid @RequestBody CreateCourseDto course) {
        return courseService.createCourse(course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto deleteCourse(@PathVariable(name = "id") Long courseId) {
        return courseMapper.mapTo(courseService.deleteCourse(courseId));
    }

    @PutMapping(path = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto updateCourse(@Valid @PathVariable(name = "id") Long courseId,
                                  @Valid @RequestBody CourseDto course) {
        return courseMapper.mapTo(courseService.updateCourse(courseId, courseMapper.mapFrom(course)));
    }
}
