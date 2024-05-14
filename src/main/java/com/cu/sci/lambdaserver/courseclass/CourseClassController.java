package com.cu.sci.lambdaserver.courseclass;

import com.cu.sci.lambdaserver.courseclass.dto.*;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.courseclass.service.CourseClassService;
import com.cu.sci.lambdaserver.courseclass.service.CourseClassTimingService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public CourseClassDto createCourseClass(@Validated(CreateCourseClassDto.CreateValidation.class) @RequestBody CreateCourseClassDto courseClassDto) {
        return courseClassService.createCourseClass(courseClassDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseClassDto> getAllCourseClasses() {
        return courseClassService.getAllCourseClasses().stream().toList() ;
    }

    @GetMapping("/{course-code}/{group-number}")
    @ResponseStatus(HttpStatus.OK)
    public CourseClassDto getCourseClassById(@PathVariable("course-code") String courseCode, @PathVariable("group-number") Integer groupNumber) {
        return courseClassService.getCourseClass(courseCode, groupNumber);
    }

    @PatchMapping("/{course-code}/{group-number}")
    @ResponseStatus(HttpStatus.OK)
    public CourseClassDto updateCourseClass(@PathVariable("course-code") String courseCode, @PathVariable("group-number") Integer groupNumber,@Validated(CreateCourseClassDto.UpdateValidation.class) @RequestBody CreateCourseClassDto courseClassDto) {
    return courseClassService.updateCourseClass(courseCode, groupNumber, courseClassDto);
    }

    @DeleteMapping("/{course-code}/{group-number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MessageResponse deleteCourseClass(@PathVariable("course-code") String courseCode, @PathVariable("group-number") Integer groupNumber) {
        return  courseClassService.deleteCourseClass(courseCode, groupNumber);
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
