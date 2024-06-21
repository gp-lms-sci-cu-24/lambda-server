package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.courseregister.dto.CourseResultDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ICourseResultsService {
    @Transactional
    @PreAuthorize("hasAnyRole('STUDENT')")
    Set<CourseResultDto> getMyResult();

    @Transactional
    @PreAuthorize("hasAnyRole('STUDENT')")
    Set<CourseResultDto> getMyResult(Integer year, Set<YearSemester> semester);

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    Set<CourseResultDto> getStudentResult(String student);

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    Set<CourseResultDto> getStudentResult(String student, Integer year, Set<YearSemester> semester);

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN')")
    MessageResponse finishCourseClassForStudent(
            String student, Integer year, Set<YearSemester> semester, Integer group, Integer grade
    );

}
