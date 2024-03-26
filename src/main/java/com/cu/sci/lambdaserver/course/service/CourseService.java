package com.cu.sci.lambdaserver.course.service;

import com.cu.sci.lambdaserver.course.CourseRepository;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> GetCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent())
            return optionalCourse.get();
        else
            throw new IllegalStateException("this course doesn't exist");
    }


    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        if (courseRepository.existsById(courseId))
            courseRepository.deleteById(courseId);
        else
            throw new IllegalStateException("this course doesn't exist");
    }

    @Transactional
    public void updateCourse(Long courseId,
                             String name,
                             String code,
                             String info,
                             Integer creditHours) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Course c;
        if (optionalCourse.isPresent())
            c = optionalCourse.get();
        else
            throw new IllegalStateException("this course doesn't exist");
        if (name != null &&
                !name.isEmpty()
                && !c.getName().equals(name))
            c.setName(name);
        if (code != null
                && !code.isEmpty()
                && !c.getCode().equals(code))
            c.setCode(code);
        if (info != null
                && !info.isEmpty()
                && !c.getInfo().equals(info))
            c.setInfo(info);
        if (creditHours != null
                && !c.getCreditHours().equals(creditHours))
            c.setCreditHours(creditHours);
    }
}
