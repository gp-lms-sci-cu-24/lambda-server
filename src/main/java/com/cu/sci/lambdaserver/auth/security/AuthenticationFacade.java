package com.cu.sci.lambdaserver.auth.security;

import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * This class is an implementation of the IAuthenticationFacade interface.
 * It provides methods to get the authentication and the authenticated user.
 * It is annotated with @Component to indicate that it's a Spring Bean.
 * It is annotated with @RequiredArgsConstructor to generate a constructor with required fields, in this case, IUserService.
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFacade implements IAuthenticationFacade {

    private final IUserService userService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getAuthenticatedUser() {
        return userService.loadUserByUsername(getAuthentication().getName());
    }
}
