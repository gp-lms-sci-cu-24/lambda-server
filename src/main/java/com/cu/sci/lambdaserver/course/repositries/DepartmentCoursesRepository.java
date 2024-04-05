package com.cu.sci.lambdaserver.course.repositries;

import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentCoursesRepository extends JpaRepository<DepartmentCourses, Long> {

}
