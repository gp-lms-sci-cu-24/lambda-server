package com.cu.sci.lambdaserver.auth;

import com.cu.sci.lambdaserver.auth.dto.LoginRequestDto;
import com.cu.sci.lambdaserver.auth.dto.LoginResponseDto;
import com.cu.sci.lambdaserver.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String refresh() {
        return "refresh";
    }

}
