package com.cu.sci.lambdaserver.courseclass.repository;

import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.utils.enums.CourseClassState;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

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

    Collection<CourseClass> findByCourseIdAndSemesterAndYear(Long courseId, YearSemester semester, Integer year);

    int countDistinctByCourseAndYearAndSemester(Course course, Integer year, YearSemester semester);

    /**
     * Used To check intersection in Timing
     */
    Set<CourseClass> findByStateInAndTimings_LocationAndTimings_DayAndTimings_StartTimeBeforeAndTimings_EndTimeAfter(Collection<CourseClassState> states, Location location, DayOfWeek day, Time startTime, Time endTime);

//    Optional<CourseClass> findByCourse_CodeContainsIgnoreCase(@Nullable String code);
}
