package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.Role;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthenticatedCourseRegisterResultService implements IAuthenticatedCourseRegisterResultService {

    private final IAuthenticationFacade authenticationFacade;
    private final ICourseRegisterService courseRegisterService;

    @Override
    public Set<CourseDto> getMyAvailableCourses() {
        return courseRegisterService.getStudentAvailableCourses(getCurrentStudentUsername());
    }

    @Override
    public MessageResponse takeASeatToMeAtCourseClass(String course, Integer years, YearSemester semester, Integer groupNumber) {
        return courseRegisterService.takeASeatAtCourseClass(getCurrentStudentUsername(), course, years, semester, groupNumber);
    }

    @Override
    public MessageResponse registerCourseClassToMe(String course, Integer years, YearSemester semester, Integer groupNumber) {
        return courseRegisterService.registerCourseClass(getCurrentStudentUsername(), course, years, semester, groupNumber);
    }

    @Override
    public MessageResponse removeCourseClassForMe(String course, Integer years, YearSemester semester, Integer groupNumber) {
        return courseRegisterService.removeCourseClass(getCurrentStudentUsername(), course, years, semester, groupNumber);
    }

    @Override
    public Set<CourseClassDto> getRegisteredCourseClassesToMe() {
        return courseRegisterService.getRegisteredCourseClasses(getCurrentStudentUsername());
    }

    private String getCurrentStudentUsername() {
        User user = authenticationFacade.getAuthenticatedUser();
        if (!user.hasRole(Role.STUDENT))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this resource");
        return user.getUsername();
    }
}
