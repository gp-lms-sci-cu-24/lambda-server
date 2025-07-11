package com.cu.sci.lambdaserver.course;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.service.CourseService;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = ("/api/v1/course"))
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final IMapper<Course, CourseDto> courseMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseDto> GetCourses(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "20") Integer pageSize) {
        return courseService.getCourses(pageNo, pageSize);
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

    @PostMapping("/{id}/prerequisites/{prerequisite}")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto addPrerequisite(@PathVariable(name = "id") Long courseId, @PathVariable(name = "prerequisite") Long prerequisiteId) {
        return courseMapper.mapTo(courseService.addPrerequisite(courseId, prerequisiteId));
    }

    @GetMapping("/{id}/prerequisites")
    @ResponseStatus(HttpStatus.OK)
    public Set<CourseDto> getPrerequisite(@PathVariable(name = "id") Long courseId) {
        return courseService.getPrerequisite(courseId).stream().map(courseMapper::mapTo).collect(Collectors.toSet());
    }

    @GetMapping("/{id}/prerequisites/all")
    @ResponseStatus(HttpStatus.OK)
    public Set<CourseDto> getAllPrerequisite(@PathVariable(name = "id") Long courseId) {
        return courseService.getAllPrerequisites(courseId).stream().map(courseMapper::mapTo).collect(Collectors.toSet());
    }
}
