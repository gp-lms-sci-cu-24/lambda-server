package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.courseregister.dto.CourseResultDto;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface IAuthenticatedCourseResultsService {
    @Transactional
    @PreAuthorize("hasAnyRole('STUDENT')")
    Set<CourseResultDto> getMyResult();

    @Transactional
    @PreAuthorize("hasAnyRole('STUDENT')")
    Set<CourseResultDto> getMyResult(Integer year, Set<YearSemester> semester);

}
