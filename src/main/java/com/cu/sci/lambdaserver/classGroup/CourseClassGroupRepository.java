package com.cu.sci.lambdaserver.classGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CourseClassGroupRepository extends JpaRepository<CourseClassGroup, Long> {
    // You can add custom query methods here if needed
}