package com.cu.sci.lambdaserver.auth.security;

import com.cu.sci.lambdaserver.auth.utils.WWWAuthenticateHeaderBuilder;
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

/**
 * CustomOAuth2AccessDeniedHandler is a class that implements AccessDeniedHandler interface.
 * It handles the AccessDeniedException by providing a custom response.
 */
@Slf4j
@Setter
@Component
public class CustomOAuth2AccessDeniedHandler implements AccessDeniedHandler {

    // The realm name for the OAuth2 authentication
    private String realmName;

    /**
     * This method handles the AccessDeniedException.
     * It logs the error message, prepares a custom error response and sends it back to the client.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @param e        The AccessDeniedException
     * @throws IOException If an input or output exception occurred
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException {
        // Log the error message
        log.error(e.getLocalizedMessage());

        // Prepare a map to hold the parameters for the WWW-Authenticate header
        Map<String, String> parameters = new LinkedHashMap<>();
        String errorMessage = e.getLocalizedMessage();

        // If realmName is not null, add it to the parameters
        if (Objects.nonNull(realmName)) {
            parameters.put("realm", realmName);
        }

        // If the user principal is an instance of AbstractOAuth2TokenAuthenticationToken,
        // update the error message and add additional parameters
        if (request.getUserPrincipal() instanceof AbstractOAuth2TokenAuthenticationToken<?>) {
            errorMessage = "The request requires higher privileges than provided by the access token.";
            parameters.put("error", "insufficient_scope");
            parameters.put("error_description", errorMessage);
            parameters.put("error_uri", "https://tools.ietf.org/html/rfc6750#section-3.1");
        }

        // Build the error response
        String message = RestErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message(errorMessage)
                .path(request.getRequestURI())
                .build().json();

        // Compute the WWW-Authenticate header value
        String wwwAuthenticate = WWWAuthenticateHeaderBuilder
                .computeWWWAuthenticateHeaderValue(parameters);

        // Set the response headers and status
        response.addHeader("WWW-Authenticate", wwwAuthenticate);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);

        // Write the error response to the response writer
        response.getWriter().write(message);
    }
}