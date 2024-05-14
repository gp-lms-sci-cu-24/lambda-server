package com.cu.sci.lambdaserver.course.repositries;

import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentCoursesRepository extends JpaRepository<DepartmentCourses, Long> {
    Optional<DepartmentCourses> findByCourseCodeAndDepartmentCode(String courseCode, String departmentCode);
}
