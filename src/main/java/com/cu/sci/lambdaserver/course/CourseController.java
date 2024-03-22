package com.cu.sci.lambdaserver.course;

import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = ("/api/courses"))
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final iMapper<Course, CourseDto> courseMapper;

    @GetMapping
    public List<CourseDto> GetCourses() {
        List<Course> l = courseService.GetCourses();
        return l.stream().map(courseMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable(name = "id") Long courseId) {
        return courseMapper.mapTo(courseService.getCourse(courseId));
    }

    @PostMapping(path = "/add")
    public void addCourse(@Valid @RequestBody CourseDto course) {
        courseService.addCourse(courseMapper.mapFrom(course));
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable(name = "id") Long courseId) {
        courseService.deleteCourse(courseId);
    }

    @PutMapping(path = "/update/{id}")
    public void updateCourse(@Valid @PathVariable(name = "id") Long courseId,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String code,
                             @RequestParam(required = false) String info,
                             @RequestParam(required = false) Integer creditHours) {
        courseService.updateCourse(courseId, name, code, info, creditHours);
    }
}
