package com.cu.sci.lambdaserver.course.repositries;

import com.cu.sci.lambdaserver.course.entites.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>,PagingAndSortingRepository<Course, Long> {
    Optional<Course> findByCodeIgnoreCase(String Code);

    boolean existsByCodeIgnoreCase(String code);

    Page<Course> findDistinctByNameLikeIgnoreCaseOrCodeLikeIgnoreCase(String name, String code, Pageable pageable);
}
