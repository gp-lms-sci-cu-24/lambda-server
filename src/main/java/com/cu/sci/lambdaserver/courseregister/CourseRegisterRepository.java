package com.cu.sci.lambdaserver.courseregister;

import com.cu.sci.lambdaserver.utils.enums.Semester;
import com.cu.sci.lambdaserver.utils.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CourseRegisterRepository extends JpaRepository<CourseRegister, Long>, PagingAndSortingRepository<CourseRegister,Long> {
    Collection<CourseRegister> findCourseRegisterByStudentId(Long studentId);
    Collection<CourseRegister> findCourseRegisterByStudentIdAndCourseClass_CourseSemester(Long studentId, Semester semester);

    Collection<CourseRegister> findCourseRegisterByStudent_Code(String code);
    Collection<CourseRegister> findCourseRegisterByStudent_CodeAndCourseClass_CourseState(String code, State semester);
}
