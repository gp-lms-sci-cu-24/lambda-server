package com.cu.sci.lambdaserver.auth.service;

import com.cu.sci.lambdaserver.auth.dto.ClientInfoDto;
import com.cu.sci.lambdaserver.auth.dto.LoginRequestDto;
import com.cu.sci.lambdaserver.auth.dto.LoginResponseDto;
import com.cu.sci.lambdaserver.auth.dto.SignOutResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;


/**
 * This interface defines the authentication services that are required for the application.
 * It includes methods for signing in, refreshing the authentication token and signing out.
 */
public interface IAuthService {

    /**
     * This method is used to sign in a user.
     *
     * @param loginRequestDto This is the first parameter to signIn method which includes the login credentials.
     * @param clientInfoDto   This is the second parameter to signIn method which includes the client information.
     * @return ResponseEntity<LoginResponseDto> This returns the response entity with the login response data transfer object.
     */
    ResponseEntity<LoginResponseDto> signIn(LoginRequestDto loginRequestDto, ClientInfoDto clientInfoDto);

    /**
     * This method is used to refresh the JWT authentication token.
     *
     * @param jwtAuthenticationToken This is the first parameter to refresh method which includes the JWT authentication token.
     * @param clientInfo             This is the second parameter to refresh method which includes the client information.
     * @return ResponseEntity<LoginResponseDto> This returns the response entity with the login response data transfer object.
     */
    ResponseEntity<LoginResponseDto> refresh(JwtAuthenticationToken jwtAuthenticationToken, ClientInfoDto clientInfo);

    /**
     * This method is used to sign out a user.
     *
     * @param jwtAuthenticationToken This is the first parameter to signOut method which includes the JWT authentication token.
     * @param clientInfo             This is the second parameter to signOut method which includes the client information.
     * @return ResponseEntity<SignOutResponseDto> This returns the response entity with the sign out response data transfer object.
     */
    ResponseEntity<SignOutResponseDto> signOut(JwtAuthenticationToken jwtAuthenticationToken, ClientInfoDto clientInfo);
}