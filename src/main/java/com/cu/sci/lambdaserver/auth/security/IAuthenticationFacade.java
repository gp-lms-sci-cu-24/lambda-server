package com.cu.sci.lambdaserver.auth.security;

import com.cu.sci.lambdaserver.user.User;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    User getAuthenticatedUser();
}
