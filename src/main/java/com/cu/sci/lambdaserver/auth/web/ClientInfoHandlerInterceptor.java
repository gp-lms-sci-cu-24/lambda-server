package com.cu.sci.lambdaserver.auth.web;

import com.cu.sci.lambdaserver.auth.dto.ClientInfoDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * This class is a handler interceptor for client information.
 * It implements the HandlerInterceptor interface.
 */
@Component
public class ClientInfoHandlerInterceptor implements HandlerInterceptor {

    /**
     * This method is used to handle the pre-processing of the request.
     * It extracts the client information and sets it as a request attribute.
     *
     * @param request  This is the first parameter to preHandle method which includes the HTTP servlet request.
     * @param response This is the second parameter to preHandle method which includes the HTTP servlet response.
     * @param handler  This is the third parameter to preHandle method which includes the handler object.
     * @return boolean This returns true to indicate that the execution chain should proceed with the next interceptor or the handler itself.
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        ClientInfoDto clientInfoDto = ClientInfoDto.builder()
                .ipAddress(getIpAddress(request))
                .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
                .build();

        request.setAttribute("clientInfo", clientInfoDto);
        return true;
    }

    /**
     * This method is used to get the IP address from the request.
     * It first checks the "X-Forwarded-For" header for the IP address. If not found, it uses the remote address of the request.
     *
     * @param request This is the first parameter to getIpAddress method which includes the HTTP servlet request.
     * @return String This returns the IP address.
     */
    private String getIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null) {
            return xForwardedFor.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}