package com.cu.sci.lambdaserver.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    public List<Course> GetCourses(){
        return courseRepository.findAll();
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }
}
