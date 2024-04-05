package com.cu.sci.lambdaserver.courseclasstiming;

import com.cu.sci.lambdaserver.courseclasstiming.dto.CourseClassTimingInDto;
import com.cu.sci.lambdaserver.courseclasstiming.dto.CourseClassTimingOutDto;
import com.cu.sci.lambdaserver.courseclasstiming.service.CourseClassTimingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/courseTiming")
@RequiredArgsConstructor
public class CourseClassTimingController {
    private CourseClassTimingService courseClassTimingService;

    @Autowired
    public CourseClassTimingController(CourseClassTimingService courseClassTimingService) {
        this.courseClassTimingService = courseClassTimingService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseClassTimingOutDto> getCourseClassTiming() {
        return courseClassTimingService.getAllCourseClassTiming();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseClassTimingOutDto createCourseClassTiming(@Valid @RequestBody CourseClassTimingInDto courseClassTimingInDto) {
        return courseClassTimingService.addCourseClassTiming(courseClassTimingInDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseClassTimingOutDto getCourseClassTimingById(@PathVariable Long id) {
        return courseClassTimingService.getCourseClassTimingById(id);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CourseClassTimingOutDto updateCourseClassTiming(@PathVariable Long id, @RequestBody CourseClassTimingInDto courseClassTimingInDto) {
        return courseClassTimingService.updateCourseClassTiming(id, courseClassTimingInDto);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CourseClassTimingOutDto deleteCourseClassTiming(@PathVariable Long id) {
        return courseClassTimingService.deleteCourseClassTiming(id);
    }
}
