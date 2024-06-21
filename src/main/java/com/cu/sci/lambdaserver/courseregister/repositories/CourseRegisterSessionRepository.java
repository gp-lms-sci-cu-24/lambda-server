package com.cu.sci.lambdaserver.courseregister.repositories;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseregister.entities.CourseRegisterSession;
import com.cu.sci.lambdaserver.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface CourseRegisterSessionRepository extends JpaRepository<CourseRegisterSession, String> {


    int countDistinctByCourseClassAndCreatedAtAfterAllIgnoreCase(CourseClass courseClass, ZonedDateTime createdAt);

    Optional<CourseRegisterSession> findByStudentAndCourseClassAndCreatedAtAfter(Student student, CourseClass courseClass, ZonedDateTime createdAt);

}