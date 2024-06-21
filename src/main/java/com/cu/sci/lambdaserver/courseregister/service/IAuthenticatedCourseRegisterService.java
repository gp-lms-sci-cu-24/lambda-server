package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface IAuthenticatedCourseRegisterService {
    @Transactional
    @PreAuthorize("hasAnyRole('STUDENT')")
    Set<CourseDto> getMyAvailableCourses();

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @PreAuthorize("hasAnyRole('STUDENT')")
    MessageResponse takeASeatToMeAtCourseClass(String course, Integer years, YearSemester semester, Integer groupNumber);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @PreAuthorize("hasAnyRole('STUDENT')")
    MessageResponse registerCourseClassToMe(String course, Integer years, YearSemester semesters, Integer groupNumber);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @PreAuthorize("hasAnyRole('STUDENT')")
    MessageResponse removeCourseClassForMe(String course, Integer years, YearSemester semesters, Integer groupNumber);

    @Transactional
    @PreAuthorize("hasAnyRole('STUDENT')")
    Set<CourseClassDto> getRegisteredCourseClassesToMe();
}
