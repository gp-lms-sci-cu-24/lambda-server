package com.cu.sci.lambdaserver.courseregister.repositories;

import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.courseregister.entities.CourseRegister;
import com.cu.sci.lambdaserver.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CourseRegisterRepository extends JpaRepository<CourseRegister, Long>, PagingAndSortingRepository<CourseRegister, Long> {
//    Collection<CourseRegister> findAllByStudentId(Long studentId);

//    Optional<CourseRegister> findByCourseClassCourseAndState(Course course , CourseRegisterState state);

//    Collection<CourseRegister> findAllByCourseClass_Id(Long courseClassId);

//    Collection<CourseRegister> findAllByStudent_CodeAndState(String studentCode, CourseRegisterState state);

//    Collection<CourseRegister> findByCourseClassYearAndCourseClassSemesterAndStudentIdAndStateIn(Integer Year, YearSemester semester, Long studentId, Collection<CourseRegisterState> states);

//    Optional<CourseRegister> findByCourseClass_IdAndStudent_CodeAndState(Long courseClassId, String studentCode, CourseRegisterState state);

    boolean existsByStudentAndCourseClassCourse(Student student, Course course);

    Set<CourseRegister> findAllByStudent(Student student);


}
