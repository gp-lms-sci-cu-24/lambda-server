package com.cu.sci.lambdaserver.course.service;

import com.cu.sci.lambdaserver.course.entites.Course;

import java.util.List;

public interface ICourseService {
    List<Course> getCourses();

    Course getCourse(Long courseId);

    Course createCourse(Course course);

    Course deleteCourse(Long courseId);

    Course updateCourse(Long courseId, Course course);
}
