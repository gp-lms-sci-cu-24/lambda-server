package com.cu.sci.lambdaserver.auth.security;

import com.cu.sci.lambdaserver.user.User;
import org.springframework.security.core.Authentication;

/**
 * This interface represents the authentication facade.
 * It provides methods to get the authentication and the authenticated user.
 */
public interface IAuthenticationFacade {

    /**
     * This method is used to get the authentication.
     *
     * @return Authentication This returns the authentication.
     */
    Authentication getAuthentication();

    /**
     * This method is used to get the authenticated user.
     *
     * @return User This returns the authenticated user.
     */
    User getAuthenticatedUser();
}