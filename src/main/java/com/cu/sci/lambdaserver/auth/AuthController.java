package com.cu.sci.lambdaserver.auth;

import com.cu.sci.lambdaserver.auth.dto.LoginRequestDto;
import com.cu.sci.lambdaserver.auth.dto.LoginResponseDto;
import com.cu.sci.lambdaserver.auth.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * AuthController is a REST controller that handles authentication-related requests.
 * It includes endpoints for signing in, refreshing the authentication, and signing out.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    // The AuthService for handling authentication services
    private final IAuthService authService;

    /**
     * This endpoint handles the sign-in process for a user.
     * It takes a LoginRequestDto object and returns a LoginResponseDto object.
     *
     * @param loginRequestDto The LoginRequestDto object containing the user's login credentials
     * @return A LoginResponseDto object containing the response of the login process
     */
    @PostMapping
    public LoginResponseDto signIn(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return authService.signIn(loginRequestDto);
    }

    /**
     * This endpoint handles the refresh process for a user's authentication.
     * It takes a Principal object and a JwtAuthenticationToken object and returns a LoginResponseDto object.
     *
     * @param principal The Principal object representing the user
     * @param token     The JwtAuthenticationToken object representing the user's authentication token
     * @return A LoginResponseDto object containing the response of the refresh process
     */
    @PostMapping("/refresh")
    public LoginResponseDto refresh(Principal principal,JwtAuthenticationToken token){
        return authService.refresh(principal,token);
    }

    /**
     * This endpoint handles the sign-out process for a user.
     * It returns a string indicating the result of the sign-out process.
     *
     * @return A string indicating the result of the sign-out process
     */
    @PostMapping("/signOut")
    public String signOut() {
        return authService.signOut();
    }
}