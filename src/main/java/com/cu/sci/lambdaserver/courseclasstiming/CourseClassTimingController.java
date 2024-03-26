package com.cu.sci.lambdaserver.courseclasstiming;

import com.cu.sci.lambdaserver.courseclasstiming.service.CourseClassTimingServices;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/courseTiming")
public class CourseClassTimingController {
    private CourseClassTimingServices courseClassTimingServices;
    private iMapper<CourseClassTiming, CourseClassTimingDto> Mapper;

    public CourseClassTimingController(CourseClassTimingServices courseClassTimingServices, iMapper<CourseClassTiming, CourseClassTimingDto> mapper) {
        this.courseClassTimingServices = courseClassTimingServices;
        Mapper = mapper;
    }

    @GetMapping
    public List<CourseClassTimingDto> getCourseClassTiming() {
        return courseClassTimingServices.getAllCourseClassTiming().stream().map(Mapper::mapTo).collect(Collectors.toList());
    }

    @PostMapping
    public CourseClassTiming createCourseClassTiming(@Valid @RequestBody CourseClassTimingDto courseClassTimingDto) {
        return courseClassTimingServices.addCourseClassTiming(Mapper.mapFrom(courseClassTimingDto));
    }

    @GetMapping("/{id}")
    public CourseClassTimingDto getCourseClassTimingById(@PathVariable Long id) {
        return Mapper.mapTo(courseClassTimingServices.getCourseClassTimingById(id));
    }

    @PutMapping({"/{id}"})
    public CourseClassTimingDto updateCourseClassTiming(@PathVariable Long id, @RequestBody CourseClassTimingDto courseClassTimingDto) {
        return Mapper.mapTo(courseClassTimingServices.updateCourseClassTiming(id, Mapper.mapFrom(courseClassTimingDto)));
    }

    @DeleteMapping({"/{id}"})
    public CourseClassTimingDto deleteCourseClassTiming(@PathVariable Long id) {
        return Mapper.mapTo(courseClassTimingServices.deleteCourseClassTiming(id));
    }
}
