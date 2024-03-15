package com.cu.sci.lambdaserver.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Course getCourse(Long courseId){
        /* @TODO: Refactor code to check optional isPresent*/
        if(courseRepository.existsById(courseId))
            return courseRepository.findById(courseId).get();
        else
            throw new IllegalStateException("this course doesn't exist");
    }
    public void addCourse(Course course) {
        courseRepository.save(course);
    }
    public void deleteCourse(Long courseId){
        if(courseRepository.existsById(courseId))
            courseRepository.deleteById(courseId);
        else
            throw new IllegalStateException("this course doesn't exist");
    }
    @Transactional
    public void updateCourse(Long courseId,
                             String name,
                             String code){
        Course c;
        /* @TODO: Refactor code to check optional isPresent*/
        if(courseRepository.existsById(courseId))
            c=courseRepository.findById(courseId).get();
        else
            throw new IllegalStateException("this course doesn't exist");
        if(name!=null&&
                !name.isEmpty()
                &&!c.getName().equals(name))
            c.setName(name);
        if(code!=null
                &&!code.isEmpty()
                &&!c.getCode().equals(code))
            c.setCode(code);
//        if(department!=null
//                &&department.length()>0
//                &&!c.getDepartment().equals(department))
//            c.setDepartment(department);
    }
}
