package com.cu.sci.lambdaserver.course;
import com.cu.sci.lambdaserver.course.entites.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CourseRepository extends JpaRepository<Course,Long>{

}
