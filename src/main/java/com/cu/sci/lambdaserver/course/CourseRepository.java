package com.cu.sci.lambdaserver.course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CourseRepository extends JpaRepository<Course,Long>{

}
