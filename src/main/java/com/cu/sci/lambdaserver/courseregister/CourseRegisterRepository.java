package com.cu.sci.lambdaserver.courseregister;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CourseRegisterRepository extends JpaRepository<CourseRegister, Long>, PagingAndSortingRepository<CourseRegister, Long> {
    Collection<CourseRegister> findAllByStudentId(Long studentId);

    Collection<CourseRegister> findAllByCourseClass_CourseClassId(Long courseClassId);
}
