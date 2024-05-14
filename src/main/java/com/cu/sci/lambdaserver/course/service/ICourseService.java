package com.cu.sci.lambdaserver.course.service;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

public interface ICourseService {

    @PreAuthorize("hasRole('ADMIN')")
    Page<CourseDto> getCourses(Integer pageNo, Integer pageSize);

    @PreAuthorize("hasRole('ADMIN')")
    Course getCourse(String courseCode);

    @PreAuthorize("hasRole('ADMIN')")
    CourseDto createCourse(CreateCourseDto createCourseDto);

    @PreAuthorize("hasRole('ADMIN')")
    Course deleteCourse(String courseCode);

    @PreAuthorize("hasRole('ADMIN')")
    Course updateCourse(String courseCode, Course course);

    @PreAuthorize("hasRole('ADMIN')")
    Course addPrerequisite(String courseCode, String prerequisiteCode);

    @PreAuthorize("hasRole('ADMIN')")
    Set<Course> getPrerequisite(String courseCode);

    @PreAuthorize("hasRole('ADMIN')")
    Set<Course> getAllPrerequisites(String courseCode);

    @PreAuthorize("hasRole('ADMIN')")
    Course removePrerequisite(String courseCode, String prerequisiteCode);

    @PreAuthorize("hasRole('ADMIN')")
    DepartmentCoursesCollectingDto changeMandatoryAndSemester(String courseCode, String departmentCode, Boolean mandatory, String semester);

}
