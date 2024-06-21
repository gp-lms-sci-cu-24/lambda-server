package com.cu.sci.lambdaserver.courseregister.service.impl;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedAccessService;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticatedAccessService implements IAuthenticatedAccessService {

    private final IAuthenticationFacade authenticationFacade;

    @Override
    public void checkAccessRegisterOrResultResource(String studentUsername) {

        User user = authenticationFacade.getAuthenticatedUser();

        if (user.hasRole(Role.STUDENT)) {
            if (!user.getUsername().equalsIgnoreCase(studentUsername))
                throw forbiddenException();
        } else if (
                !user.hasRole(Role.ADMIN) &&
                        !user.hasRole(Role.STUDENT_AFFAIR) &&
                        !user.hasRole(Role.ACADEMIC_ADVISOR)
        ) {
            throw forbiddenException();
        }
    }

    @Override
    public String getCurrentStudentUsername() {
        User user = authenticationFacade.getAuthenticatedUser();
        if (!user.hasRole(Role.STUDENT))
            throw forbiddenException();
        return user.getUsername();
    }

    private ResponseStatusException forbiddenException() {
        return new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this resource");
    }
}
