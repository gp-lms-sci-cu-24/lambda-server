package com.cu.sci.lambdaserver.auth.service;


import com.cu.sci.lambdaserver.auth.dto.LoginRequestDto;
import com.cu.sci.lambdaserver.auth.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        log.info("login request: {}", loginRequestDto);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.username(),
                        loginRequestDto.password()
                )
        );


        return LoginResponseDto.builder()
                .accessToken(jwtTokenService.generateAccessToken(authentication))
                .refreshToken("refresh")
                .build();
    }

    public String logout() {
        return "logout";
    }

    public String refresh() {
        return "refresh";
    }

    public String register() {
        return "register";
    }

    public String forgotPassword() {
        return "forgot-password";
    }
}
