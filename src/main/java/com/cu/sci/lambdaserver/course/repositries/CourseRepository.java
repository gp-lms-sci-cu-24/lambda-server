package com.cu.sci.lambdaserver.course.repositries;

import com.cu.sci.lambdaserver.course.entites.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>,PagingAndSortingRepository<Course, Long> {
    Optional<Course> findByCode(String Code);
}
