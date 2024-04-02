package com.cu.sci.lambdaserver.auth.service;

import com.cu.sci.lambdaserver.auth.dto.LoginRequestDto;
import com.cu.sci.lambdaserver.auth.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * AuthService is a service class that implements the IAuthService interface.
 * It provides methods for user authentication, including signing in, refreshing the authentication, and signing out.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final IJwtTokenService jwtTokenService;

    /**
     * This method handles the sign-in process for a user.
     * It takes a LoginRequestDto object, authenticates the user, generates JWT access and refresh tokens,
     * and returns a LoginResponseDto object.
     *
     * @param loginRequestDto The LoginRequestDto object containing the user's login credentials
     * @return A LoginResponseDto object containing the response of the login process
     */
    @Override
    public LoginResponseDto signIn(LoginRequestDto loginRequestDto) {

        log.info("login request: {}", loginRequestDto);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.username(),
                        loginRequestDto.password()
                )
        );

        final String refresh =jwtTokenService.generateRefreshToken(authentication);

        return LoginResponseDto.builder()
                .accessToken(jwtTokenService.generateAccessToken(authentication))
                .refreshToken(refresh)
                .build();
    }

    /**
     * This method handles the refresh process for a user's authentication.
     * It takes a Principal object and a JwtAuthenticationToken object and returns a LoginResponseDto object.
     *
     * @param principal              The Principal object representing the user
     * @param jwtAuthenticationToken The JwtAuthenticationToken object representing the user's authentication token
     * @return A LoginResponseDto object containing the response of the refresh process
     */
    @Override
    public LoginResponseDto refresh(Principal principal, JwtAuthenticationToken jwtAuthenticationToken) {

        String tokenId = jwtAuthenticationToken.getToken().getId();

        return LoginResponseDto.builder().build();
    }

    /**
     * This method handles the sign-out process for a user.
     * It returns a string indicating the result of the sign-out process.
     *
     * @return A string indicating the result of the sign-out process
     */
    @Override
    public String signOut() {
        return "logout";
    }

}