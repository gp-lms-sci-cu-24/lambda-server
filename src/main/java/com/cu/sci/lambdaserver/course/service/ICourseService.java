package com.cu.sci.lambdaserver.course.service;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ICourseService {
    Page<CourseDto> getCourses(Integer pageNo, Integer pageSize);

    Course getCourse(String courseCode);

    CourseDto createCourse(CreateCourseDto createCourseDto);

    Course deleteCourse(String courseCode);

    Course updateCourse(String courseCode, Course course);

    Course addPrerequisite(String courseCode, String prerequisiteCode);

    Set<Course> getPrerequisite(String courseCode);

    Set<Course> getAllPrerequisites(String courseCode);

    Course removePrerequisite(String courseCode, String prerequisiteCode);

    DepartmentCoursesCollectingDto changeMandatoryAndSemester(String courseCode, String departmentCode, Boolean mandatory, String semester);

}
