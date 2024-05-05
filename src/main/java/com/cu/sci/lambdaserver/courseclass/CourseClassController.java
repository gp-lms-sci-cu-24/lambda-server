package com.cu.sci.lambdaserver.courseclass;

import com.cu.sci.lambdaserver.courseclass.dto.*;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.courseclass.service.CourseClassService;
import com.cu.sci.lambdaserver.courseclass.service.CourseClassTimingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/course-class")
@RequiredArgsConstructor
public class CourseClassController {
    private final CourseClassMapper courseClassMapper;
    private final CourseClassService courseClassService;
    private final CourseClassTimingService courseClassTimingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseClassResponse createCourseClass(@Validated(CourseClassInDto.CreateValidation.class) @RequestBody CourseClassDto courseClassDto) {
        return courseClassService.createCourseClass(courseClassDto);
    }

    @GetMapping
    public ResponseEntity<List<CourseClassDto>> getAllCourseClasses() {
        List<CourseClass> courseClasses = courseClassService.getAllCourseClasses();
        return new ResponseEntity<>(courseClasses.stream().map(courseClassMapper::mapTo).collect(Collectors.toList())
                , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseClassDto getCourseClassById(@PathVariable Long id) {
        return courseClassMapper.mapTo(courseClassService.getCourseClassById(id));
    }

    @PatchMapping
    public ResponseEntity<CourseClassDto> updateCourseClass(@Validated(CourseClassInDto.UpdateValidation.class) @RequestBody CourseClassDto courseClassDto) {
        CourseClass updatedCourseClass = courseClassService.updateCourseClass(courseClassDto);
        return new ResponseEntity<>(courseClassMapper.mapTo(updatedCourseClass), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourseClass(@PathVariable Long id) {
        if (!courseClassService.isCourseClassExists(id)) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        courseClassService.deleteCourseClass(id);
        return new ResponseEntity<>("The record was deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/timing")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseClassTimingOutDto> getCourseClassTiming() {
        return courseClassTimingService.getAllCourseClassTiming();
    }

    @PostMapping("/timing")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseClassTimingOutDto createCourseClassTiming(@Valid @RequestBody CourseClassTimingInDto courseClassTimingInDto) {
        return courseClassTimingService.addCourseClassTiming(courseClassTimingInDto);
    }

    @GetMapping("/timing/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseClassTimingOutDto getCourseClassTimingById(@PathVariable Long id) {
        return courseClassTimingService.getCourseClassTimingById(id);
    }

    @PutMapping({"/timing/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CourseClassTimingOutDto updateCourseClassTiming(@PathVariable Long id, @RequestBody CourseClassTimingInDto courseClassTimingInDto) {
        return courseClassTimingService.updateCourseClassTiming(id, courseClassTimingInDto);
    }

    @DeleteMapping({"/timing/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CourseClassTimingOutDto deleteCourseClassTiming(@PathVariable Long id) {
        return courseClassTimingService.deleteCourseClassTiming(id);
    }
}
