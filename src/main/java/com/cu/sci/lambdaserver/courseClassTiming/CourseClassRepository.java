package com.cu.sci.lambdaserver.courseClassTiming;

import com.cu.sci.lambdaserver.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseClassRepository extends JpaRepository<Course, Long> {
    
}
