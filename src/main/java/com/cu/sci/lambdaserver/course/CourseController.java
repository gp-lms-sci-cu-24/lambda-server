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
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable(name = "id") Long courseId){
        return courseService.getCourse(courseId);
    }

    @PostMapping(path = "/add")
    public void addCourse(@RequestBody Course course){
        courseService.addCourse(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable(name = "id") Long courseId){
        courseService.deleteCourse(courseId);
    }
    @PutMapping(path = "/update/{id}")
    public void updateCourse(@PathVariable(name = "id") Long courseId,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false)String code){
        courseService.updateCourse(courseId,name,code);
    }
}
