package com.cu.sci.lambdaserver.course.service;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.CreateCourseRequestDto;
import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ICourseService {

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    CourseDto createCourse(CreateCourseRequestDto createCourseDto);

    @PreAuthorize("hasRole('ADMIN')")
    Page<CourseDto> getCourses(Integer pageNo, Integer pageSize);

    @PreAuthorize("hasRole('ADMIN')")
    CourseDto getCourseByCode(String code);

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse deleteCourseByCode(String courseCode);

    @PreAuthorize("hasRole('ADMIN')")
    Course updateCourse(String courseCode, Course course);

    @PreAuthorize("hasRole('ADMIN')")
    Course addPrerequisite(String courseCode, String prerequisiteCode);

    @PreAuthorize("hasRole('ADMIN')")
    Collection<Course> getPrerequisite(String courseCode);

    @PreAuthorize("hasRole('ADMIN')")
    Collection<Course> getAllPrerequisites(String courseCode);

    @PreAuthorize("hasRole('ADMIN')")
    Course removePrerequisite(String courseCode, String prerequisiteCode);

    @PreAuthorize("hasRole('ADMIN')")
    DepartmentCoursesCollectingDto changeMandatoryAndSemester(String courseCode, String departmentCode, Boolean mandatory, String semester);

}
