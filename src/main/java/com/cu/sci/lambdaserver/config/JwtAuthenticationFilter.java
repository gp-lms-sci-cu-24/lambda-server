package com.cu.sci.lambdaserver.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService ;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull   HttpServletResponse response,
            @NonNull   FilterChain filterChain
    ) throws ServletException, IOException {
        // TODO here we need to pass JWT authentication token within the header

        final String authHeader = request.getHeader("Authorization");

        final String jwt;
        final  String userId;
        if(authHeader==null||!authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        //TODO here we need to extract the user id from JWT token
        userId = jwtService.extractUserId(jwt);

        //TODO @ HNOONa-0 - Continue implementation here

//        if(userId!=null && SecurityContextHolder.getContext().getAuthentication()==null){
//            UserDetails userDetails;
//
//        }
    }
}
