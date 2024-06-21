package com.cu.sci.lambdaserver.courseregister.service.impl;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedAccessService;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedCourseRegisterService;
import com.cu.sci.lambdaserver.courseregister.service.ICourseRegisterService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthenticatedCourseRegisterService implements IAuthenticatedCourseRegisterService {

    private final IAuthenticatedAccessService authenticatedAccessService;
    private final ICourseRegisterService courseRegisterService;

    @Override
    public Set<CourseDto> getMyAvailableCourses() {
        return courseRegisterService.getStudentAvailableCourses(
                authenticatedAccessService.getCurrentStudentUsername()
        );
    }

    @Override
    public MessageResponse takeASeatToMeAtCourseClass(String course, Integer years, YearSemester semester, Integer groupNumber) {
        return courseRegisterService.takeASeatAtCourseClass(
                authenticatedAccessService.getCurrentStudentUsername()
                , course, years, semester, groupNumber
        );
    }

    @Override
    public MessageResponse registerCourseClassToMe(String course, Integer years, YearSemester semester, Integer groupNumber) {
        return courseRegisterService.registerCourseClass(
                authenticatedAccessService.getCurrentStudentUsername()
                , course, years, semester, groupNumber
        );
    }

    @Override
    public MessageResponse removeCourseClassForMe(String course, Integer years, YearSemester semester, Integer groupNumber) {
        return courseRegisterService.removeCourseClass(
                authenticatedAccessService.getCurrentStudentUsername(),
                course, years, semester, groupNumber
        );
    }

    @Override
    public Set<CourseClassDto> getRegisteredCourseClassesToMe() {
        return courseRegisterService.getRegisteredCourseClasses(
                authenticatedAccessService.getCurrentStudentUsername()
        );
    }


}
