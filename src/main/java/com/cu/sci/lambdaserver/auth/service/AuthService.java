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

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService  {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;


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

    public LoginResponseDto refresh(Principal principal, JwtAuthenticationToken jwtAuthenticationToken) {

        String tokenId = jwtAuthenticationToken.getToken().getId();



        return LoginResponseDto.builder().build();
    }

    public String signOut() {
        return "logout";
    }

}
