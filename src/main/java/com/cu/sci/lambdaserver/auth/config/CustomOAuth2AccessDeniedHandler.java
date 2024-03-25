package com.cu.sci.lambdaserver.auth.config;

import com.cu.sci.lambdaserver.utils.exception.RestErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Setter
@Component
public class CustomOAuth2AccessDeniedHandler implements AccessDeniedHandler {

    private String realmName;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException {
        log.error(e.getLocalizedMessage(), e);
        Map<String, String> parameters = new LinkedHashMap<>();
        String errorMessage = e.getLocalizedMessage();
        if (Objects.nonNull(realmName)) {
            parameters.put("realm", realmName);
        }
        if (request.getUserPrincipal() instanceof AbstractOAuth2TokenAuthenticationToken<?>) {
            errorMessage = "The request requires higher privileges than provided by the access token.";
            parameters.put("error", "insufficient_scope");
            parameters.put("error_description", errorMessage);
            parameters.put("error_uri", "https://tools.ietf.org/html/rfc6750#section-3.1");
        }
        String message = RestErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message(errorMessage)
                .path(request.getRequestURI())
                .build().json();
        String wwwAuthenticate = WWWAuthenticateHeaderBuilder
                .computeWWWAuthenticateHeaderValue(parameters);
        response.addHeader("WWW-Authenticate", wwwAuthenticate);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(message);
    }
}