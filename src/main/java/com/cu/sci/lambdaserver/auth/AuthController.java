package com.cu.sci.lambdaserver.auth;

import com.cu.sci.lambdaserver.auth.dto.LoginRequestDto;
import com.cu.sci.lambdaserver.auth.dto.LoginResponseDto;
import com.cu.sci.lambdaserver.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public LoginResponseDto signIn(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.signIn(loginRequestDto);
    }

    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }

    @PostMapping("/refresh")
    public LoginResponseDto refresh(Principal principal,JwtAuthenticationToken token){
        return authService.refresh(principal,token);
    }

}
