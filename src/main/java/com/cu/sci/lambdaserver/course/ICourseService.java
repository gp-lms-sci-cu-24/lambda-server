package com.cu.sci.lambdaserver.course;

import com.cu.sci.lambdaserver.course.entites.Course;

import java.util.List;

public interface ICourseService {
    List<Course> GetCourses();

    Course getCourse(Long courseId);

    void addCourse(Course course);

    void deleteCourse(Long courseId);

    void updateCourse(Long courseId,
                      String name,
                      String code,
                      String info,
                      Integer creditHours);
}
