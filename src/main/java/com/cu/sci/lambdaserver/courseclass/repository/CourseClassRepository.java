package com.cu.sci.lambdaserver.courseclass.repository;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CourseClassRepository extends JpaRepository<CourseClass, Long> {
    // You can add custom query methods here if needed
    // change date attribute to date time
//    @Query(value = "SELECT * FROM course_classes WHERE course_id = :courseId " +
//            "AND publish_date = (select MAX(publish_date) from course_classes) " +
//            "ORDER BY course_class_id desc limit 1", nativeQuery = true)
//    Optional<CourseClass> getLatestClassByCourseId(@Param("courseId") Long courseId);

    Optional<CourseClass> findTopByCourseIdOrderByGroupNumberDesc(Long courseId) ;

    Optional<CourseClass> findByCourseIdAndGroupNumber(Long courseId, Integer groupNumber);

    Collection<CourseClass> findByCourseIdAndCourseSemesterAndYear(Long courseId, YearSemester semester, String year);
}
