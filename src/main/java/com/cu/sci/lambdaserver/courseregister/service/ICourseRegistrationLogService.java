package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseregister.entities.CourseRegisterLog;
import com.cu.sci.lambdaserver.student.Student;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

public interface ICourseRegistrationLogService {
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    void log(CourseRegisterLog.Action action, Student student, CourseClass courseClass);

}
