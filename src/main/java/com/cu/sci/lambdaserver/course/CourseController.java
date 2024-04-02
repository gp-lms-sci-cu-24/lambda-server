package com.cu.sci.lambdaserver.course;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.service.CourseService;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CourseDto>> GetCourses() {
        List<Course> l = courseService.getCourses();
        return new ResponseEntity<>(l.stream().map(courseMapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable(name = "id") Long courseId) {
        try {
            return new ResponseEntity<>(courseMapper.mapTo(courseService.getCourse(courseId)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto course) {
        try {
            return new ResponseEntity<>(courseMapper.mapTo(courseService.createCourse(courseMapper.mapFrom(course))), HttpStatus.CREATED);
        } catch (NonUniqueResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "id") Long courseId) {
        try {
            return new ResponseEntity<>(courseMapper.mapTo(courseService.deleteCourse(courseId)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateCourse(@Valid @PathVariable(name = "id") Long courseId,
                                          @Valid @RequestBody CourseDto course) {
        try {
            return new ResponseEntity<>(courseMapper.mapTo(courseService.updateCourse(courseId, courseMapper.mapFrom(course))), HttpStatus.OK);
        } catch (NonUniqueResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
