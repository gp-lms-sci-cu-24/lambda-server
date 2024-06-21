package com.cu.sci.lambdaserver.courseclass.repository;

import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.utils.enums.CourseClassState;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

//    Optional<CourseClass> findTopByCourseIdOrderByGroupNumberDesc(Long courseId) ;

    //    Page<CourseClass> findAllByStateIn(Pageable pageable,Set<CourseClassState> states);
    Page<CourseClass> findAllByStateInAndSemesterIn(Pageable pageable, Set<CourseClassState> states, Set<YearSemester> semesters);

    Page<CourseClass> findAllByStateInAndSemesterInAndAdminProfessorUsernameIgnoreCase(Pageable pageable, Set<CourseClassState> states, Set<YearSemester> semesters, String adminProfessorUsername);

    Page<CourseClass> findAllByStateInAndSemesterInAndYearIn(Pageable pageable, Set<CourseClassState> states, Set<YearSemester> semesters, Set<Integer> years);

    Page<CourseClass> findAllByStateInAndSemesterInAndYearInAndAdminProfessorUsernameIgnoreCase(
            Pageable pageable, Set<CourseClassState> states, Set<YearSemester> semesters, Set<Integer> years, String adminProfessorUsername);

    Page<CourseClass> findAllByStateInAndSemesterInAndCourseCodeIs(Pageable pageable, Set<CourseClassState> states, Set<YearSemester> semesters, String courseCode);

    Page<CourseClass> findAllByStateInAndSemesterInAndYearInAndCourseCodeIs(Pageable pageable, Set<CourseClassState> states, Set<YearSemester> semesters, Set<Integer> years, String courseCode);

    Page<CourseClass> findAllByStateInAndSemesterInAndCourseCodeIsAndAdminProfessorUsernameIgnoreCase(Pageable pageable, Set<CourseClassState> states, Set<YearSemester> semesters, String courseCode, String adminProfessorUsername);

    Page<CourseClass> findAllByStateInAndSemesterInAndYearInAndCourseCodeIsAndAdminProfessorUsernameIgnoreCase(Pageable pageable, Set<CourseClassState> states, Set<YearSemester> semesters, Set<Integer> years, String courseCode, String adminProfessorUsername);

    Optional<CourseClass> findBySemesterIsAndYearIsAndCourseCodeIsAndGroupNumberIsAllIgnoreCase(YearSemester semester, Integer year, String courseCode, Integer groupNumber);

    Optional<CourseClass> findByCourseIdAndGroupNumber(Long courseId, Integer groupNumber);

    int countDistinctByCourseAndYearAndSemester(Course course, Integer year, YearSemester semester);

    Set<CourseClass> findAllByCourseAndYearAndSemester(Course course, Integer year, YearSemester semester, Sort sort);
    /**
     * Used To check intersection in Timing
     */
    Set<CourseClass> findByStateInAndTimings_LocationAndTimings_DayAndTimings_StartTimeBeforeAndTimings_EndTimeAfter(Collection<CourseClassState> states, Location location, DayOfWeek day, Time startTime, Time endTime);

    long countByCourseAndState(Course course, CourseClassState state);
//    Collection<CourseClass> findByCourseIdAndSemesterAndYear(Long courseId, YearSemester semester, Integer year);

//    Optional<CourseClass> findByCourse_CodeContainsIgnoreCase(@Nullable String code);
}
