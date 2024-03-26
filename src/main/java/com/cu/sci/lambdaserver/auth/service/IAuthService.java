package com.cu.sci.lambdaserver.auth.service;

import com.cu.sci.lambdaserver.auth.dto.LoginRequestDto;
import com.cu.sci.lambdaserver.auth.dto.LoginResponseDto;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.security.Principal;

/**
 * IAuthService is an interface that defines the contract for authentication services.
 * It includes methods for signing in, refreshing the authentication, and signing out.
 */
public interface IAuthService {

    /**
     * This method handles the sign-in process for a user.
     * It takes a LoginRequestDto object and returns a LoginResponseDto object.
     *
     * @param loginRequestDto The LoginRequestDto object containing the user's login credentials
     * @return A LoginResponseDto object containing the response of the login process
     */
    LoginResponseDto signIn(LoginRequestDto loginRequestDto);

    /**
     * This method handles the refresh process for a user's authentication.
     * It takes a Principal object and a JwtAuthenticationToken object and returns a LoginResponseDto object.
     *
     * @param principal              The Principal object representing the user
     * @param jwtAuthenticationToken The JwtAuthenticationToken object representing the user's authentication token
     * @return A LoginResponseDto object containing the response of the refresh process
     */
    LoginResponseDto refresh(Principal principal, JwtAuthenticationToken jwtAuthenticationToken);

    /**
     * This method handles the sign-out process for a user.
     * It returns a string indicating the result of the sign-out process.
     *
     * @return A string indicating the result of the sign-out process
     */
    String signOut();
}