package com.cu.sci.lambdaserver.courseregister.repositories;

import com.cu.sci.lambdaserver.courseregister.entities.CourseResult;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.utils.enums.CourseResultState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CourseResultRepository extends JpaRepository<CourseResult, Long> {

    Set<CourseResult> findByStudentAndState(Student student, CourseResultState state);
}