package com.cu.sci.lambdaserver.course.service;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ICourseService {
    Page<CourseDto> getCourses(Integer pageNo, Integer pageSize);

    Course getCourse(Long courseId);

    CourseDto createCourse(CreateCourseDto createCourseDto);

    Course deleteCourse(Long courseId);

    Course updateCourse(Long courseId, Course course);

    Course addPrerequisite(Long courseId, Long prerequisiteId);

    Set<Course> getPrerequisite(Long id);

    Set<Course> getAllPrerequisites(Long courseId);
}
