package com.cu.sci.lambdaserver.course.service;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.CreateCourseRequestDto;
import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

public interface ICourseService {

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    CourseDto createCourse(CreateCourseRequestDto createCourseDto);

    Page<CourseDto> getCourses(Integer pageNo, Integer pageSize);

    CourseDto getCourseByCode(String code);

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse deleteCourseByCode(String courseCode);

    Set<CourseDto> search(String q);

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

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    Collection<DepartmentCourses> getAllByDepartment(String departmentCode);
}
