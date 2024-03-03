package com.cu.sci.lambdaserver.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path=("/api/courses"))
public class CourseController {
    private final CourseService courseService;
    @Autowired
    public CourseController(CourseService courseService){
        this.courseService =courseService;
    }
    @GetMapping
    public List<Course> GetCourses(){
        return courseService.GetCourses();
    }
    @PostMapping
    public void addCourse(@RequestBody Course course){
        courseService.addCourse(course);
    }
}
