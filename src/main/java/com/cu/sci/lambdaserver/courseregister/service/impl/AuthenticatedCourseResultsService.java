package com.cu.sci.lambdaserver.courseregister.service.impl;

import com.cu.sci.lambdaserver.courseregister.dto.CourseResultDto;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedAccessService;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedCourseResultsService;
import com.cu.sci.lambdaserver.courseregister.service.ICourseResultsService;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticatedCourseResultsService implements IAuthenticatedCourseResultsService {

    private final IAuthenticatedAccessService authenticatedAccessService;
    private final ICourseResultsService courseResultsService;

    @Override
    public Set<CourseResultDto> getMyResult() {
        return courseResultsService.getStudentResult(
                authenticatedAccessService.getCurrentStudentUsername()
        );
    }

    @Override
    public Set<CourseResultDto> getMyResult(Integer year, Set<YearSemester> semester) {
        return courseResultsService.getStudentResult(
                authenticatedAccessService.getCurrentStudentUsername(),
                year, semester
        );
    }
}
