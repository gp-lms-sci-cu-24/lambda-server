package com.cu.sci.lambdaserver.auth.security;

import com.cu.sci.lambdaserver.auth.properties.SecurityConfigurationProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

/**
 * This class is a resolver for bearer tokens stored in cookies.
 * It implements the BearerTokenResolver interface.
 */
@RequiredArgsConstructor
@Component
public class CookieBearerTokenResolver implements BearerTokenResolver {

    private final SecurityConfigurationProperties securityConfigurationProperties;

    /**
     * This method is used to resolve the bearer token from the request.
     * It retrieves the cookie named after the refresh cookie property and returns its value.
     *
     * @param request This is the first parameter to resolve method which includes the HTTP servlet request.
     * @return String This returns the value of the cookie, or null if the cookie is not found.
     */
    @Override
    public String resolve(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, securityConfigurationProperties.refreshCookie());
        if (cookie != null)
            return cookie.getValue();
        else
            return null;
    }
}