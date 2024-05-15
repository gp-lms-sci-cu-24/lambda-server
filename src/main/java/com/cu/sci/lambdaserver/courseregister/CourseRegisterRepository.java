package com.cu.sci.lambdaserver.courseregister;

import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.utils.enums.CourseRegisterState;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CourseRegisterRepository extends JpaRepository<CourseRegister, Long>, PagingAndSortingRepository<CourseRegister, Long> {
    Collection<CourseRegister> findAllByStudentId(Long studentId);

    Optional<CourseRegister> findByCourseClassCourseAndState(Course course , CourseRegisterState state);

    Collection<CourseRegister> findAllByCourseClass_CourseClassId(Long courseClassId);

    Collection<CourseRegister> findAllByStudent_CodeAndState(String studentCode, CourseRegisterState state);

    Collection<CourseRegister> findByCourseClassYearAndCourseClassCourseSemesterAndStudentIdAndStateIn(String Year, Semester semester, Long studentId, Collection<CourseRegisterState> states);

    Optional<CourseRegister> findByCourseClass_CourseClassIdAndStudent_CodeAndState(Long courseClassId, String studentCode , CourseRegisterState state);

}
