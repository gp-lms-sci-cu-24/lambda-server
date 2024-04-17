package com.cu.sci.lambdaserver.auth.service;

import com.cu.sci.lambdaserver.auth.dto.ClientInfoDto;
import com.cu.sci.lambdaserver.auth.dto.LoginResponseDto;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * This interface defines the authentication services that support refresh cookies.
 * It includes methods for signing in and adding a cookie, and generating a refresh cookie.
 */
public interface IAuthSupportRefreshCookie {

    /**
     * This method is used to sign in a user and add a cookie.
     *
     * @param authentication This is the first parameter to signInAndAddCookie method which includes the authentication details.
     * @param clientInfoDto  This is the second parameter to signInAndAddCookie method which includes the client information.
     * @return ResponseEntity<LoginResponseDto> This returns the response entity with the login response data transfer object.
     */
    ResponseEntity<LoginResponseDto> signInAndAddCookie(Authentication authentication, ClientInfoDto clientInfoDto);

    /**
     * This method is used to generate a refresh cookie.
     *
     * @param refreshToken This is the first parameter to generateRefreshCookie method which includes the refresh token.
     * @return ResponseCookie This returns the generated refresh cookie.
     */
    ResponseCookie generateRefreshCookie(Jwt refreshToken);
}