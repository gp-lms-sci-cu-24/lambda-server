package com.cu.sci.lambdaserver.courseclasstiming;

import com.cu.sci.lambdaserver.courseclasstiming.service.CourseClassTimingServices;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/courseTiming")
public class CourseClassTimingController {
    private CourseClassTimingServices courseClassTimingServices;
    private iMapper<CourseClassTiming, CourseClassTimingDto> Mapper;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseClassTimingDto> getCourseClassTiming() {
        return courseClassTimingServices.getAllCourseClassTiming().stream().map(Mapper::mapTo).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseClassTimingDto createCourseClassTiming(@Valid @RequestBody CourseClassTimingDto courseClassTimingDto) {
        return Mapper.mapTo(courseClassTimingServices.addCourseClassTiming(Mapper.mapFrom(courseClassTimingDto)));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseClassTimingDto getCourseClassTimingById(@PathVariable Long id) {
        return Mapper.mapTo(courseClassTimingServices.getCourseClassTimingById(id));
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CourseClassTimingDto updateCourseClassTiming(@PathVariable Long id, @RequestBody CourseClassTimingDto courseClassTimingDto) {
        return Mapper.mapTo(courseClassTimingServices.updateCourseClassTiming(id, Mapper.mapFrom(courseClassTimingDto)));
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CourseClassTimingDto deleteCourseClassTiming(@PathVariable Long id) {
        return Mapper.mapTo(courseClassTimingServices.deleteCourseClassTiming(id));
    }
}
