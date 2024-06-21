package com.cu.sci.lambdaserver.course.repositries;

import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.utils.enums.DepartmentSemester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCodeIgnoreCase(String Code);

    boolean existsByCodeIgnoreCase(String code);

    Page<Course> findDistinctByNameLikeIgnoreCaseOrCodeLikeIgnoreCase(String name, String code, Pageable pageable);

    Set<Course> findByDepartmentsDepartmentCodeIgnoreCase(String code);

    Set<Course> findByDepartmentsDepartmentCodeIgnoreCaseAndDepartmentsSemesterIn(String code, Collection<DepartmentSemester> semesters);

    Set<Course> findByDepartmentsDepartmentCodeAndCodeNotInAllIgnoreCase(String code, Set<String> codes);


}
