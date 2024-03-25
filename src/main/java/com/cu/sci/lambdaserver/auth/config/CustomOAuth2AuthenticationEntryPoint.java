package com.cu.sci.lambdaserver.auth.config;

import com.cu.sci.lambdaserver.utils.exception.RestErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Setter
@Component
public class CustomOAuth2AuthenticationEntryPoint implements AuthenticationEntryPoint {

    private String realmName;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        log.error(e.getLocalizedMessage(), e);
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String errorMessage = "Insufficient authentication details";
        Map<String, String> parameters = new LinkedHashMap<>();

        if (Objects.nonNull(realmName)) {
            parameters.put("realm", realmName);
        }
        if (e instanceof OAuth2AuthenticationException) {
            OAuth2Error error = ((OAuth2AuthenticationException) e).getError();
            parameters.put("error", error.getErrorCode());
            if (StringUtils.hasText(error.getDescription())) {
                errorMessage = error.getDescription();
                parameters.put("error_description", errorMessage);
            }
            if (StringUtils.hasText(error.getUri())) {
                parameters.put("error_uri", error.getUri());
            }
            if (error instanceof BearerTokenError bearerTokenError) {
                if (StringUtils.hasText(bearerTokenError.getScope())) {
                    parameters.put("scope", bearerTokenError.getScope());
                }
                status = ((BearerTokenError) error).getHttpStatus();
            }
        }
        String message = RestErrorResponse.builder()
                .status(status.value())
                .error("Unauthenticated")
                .message(errorMessage)
                .path(request.getRequestURI())
                .build()
                .json();

        String wwwAuthenticate = WWWAuthenticateHeaderBuilder
                .computeWWWAuthenticateHeaderValue(parameters);
        response.addHeader("WWW-Authenticate", wwwAuthenticate);
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(message);
    }
}