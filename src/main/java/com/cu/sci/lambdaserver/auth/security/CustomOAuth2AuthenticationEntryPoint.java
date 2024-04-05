package com.cu.sci.lambdaserver.auth.security;

import com.cu.sci.lambdaserver.auth.utils.WWWAuthenticateHeaderBuilder;
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

/**
 * CustomOAuth2AuthenticationEntryPoint is a class that implements AuthenticationEntryPoint interface.
 * It handles the AuthenticationException by providing a custom response.
 */
@Slf4j
@Setter
@Component
public class CustomOAuth2AuthenticationEntryPoint implements AuthenticationEntryPoint {

    // The realm name for the OAuth2 authentication
    private String realmName;

    /**
     * This method handles the AuthenticationException.
     * It logs the error message, prepares a custom error response and sends it back to the client.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @param e        The AuthenticationException
     * @throws IOException If an input or output exception occurred
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        // Log the error message
        log.error(e.getLocalizedMessage());

        // Set the default status and error message
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String errorMessage ;

        // If the error message is not null and not blank, use it as the error message
        // Otherwise, use a default error message
        if(Objects.nonNull(e.getLocalizedMessage()) && !e.getLocalizedMessage().isBlank())
            errorMessage=e.getLocalizedMessage();
        else
            errorMessage = "Insufficient authentication details";

        // Prepare a map to hold the parameters for the WWW-Authenticate header
        Map<String, String> parameters = new LinkedHashMap<>();

        // If realmName is not null, add it to the parameters
        if (Objects.nonNull(realmName)) {
            parameters.put("realm", realmName);
        }

        // If the exception is an instance of OAuth2AuthenticationException,
        // update the error message and add additional parameters
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

        // Build the error response
        String message = RestErrorResponse.builder()
                .status(status.value())
                .error("Unauthenticated")
                .message(errorMessage)
                .path(request.getRequestURI())
                .build()
                .json();

        // Compute the WWW-Authenticate header value
        String wwwAuthenticate = WWWAuthenticateHeaderBuilder
                .computeWWWAuthenticateHeaderValue(parameters);

        // Set the response headers and status
        response.addHeader("WWW-Authenticate", wwwAuthenticate);
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);

        // Write the error response to the response writer
        response.getWriter().write(message);
    }
}