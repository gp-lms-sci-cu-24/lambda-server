package com.cu.sci.lambdaserver.courseClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseClassRepository extends JpaRepository<CourseClass, Long> {
    // You can add custom query methods here if needed
}
