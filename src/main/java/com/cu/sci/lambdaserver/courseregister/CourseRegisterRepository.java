package com.cu.sci.lambdaserver.courseregister;

import com.cu.sci.lambdaserver.utils.enums.CourseRegisterState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CourseRegisterRepository extends JpaRepository<CourseRegister, Long>, PagingAndSortingRepository<CourseRegister, Long> {
    Collection<CourseRegister> findAllByStudentId(Long studentId);

    Collection<CourseRegister> findAllByCourseClass_CourseClassId(Long courseClassId);

   Collection<CourseRegister> findAllByStudent_CodeAndState(String studentCode, CourseRegisterState state);

   CourseRegister findByStudent_CodeAndState(String studentCode, CourseRegisterState state);

    Optional<CourseRegister> findByCourseClass_CourseClassIdAndStudentId(Long courseClassId , Long studentId);
}
