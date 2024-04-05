package com.cu.sci.lambdaserver.auth;

import com.cu.sci.lambdaserver.auth.dto.ClientInfoDto;
import com.cu.sci.lambdaserver.auth.dto.LoginRequestDto;
import com.cu.sci.lambdaserver.auth.dto.LoginResponseDto;
import com.cu.sci.lambdaserver.auth.dto.SignOutResponseDto;
import com.cu.sci.lambdaserver.auth.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;


    @PostMapping
    public ResponseEntity<LoginResponseDto> signIn(@RequestBody @Valid LoginRequestDto loginRequestDto,
                                                   @RequestAttribute ClientInfoDto clientInfo
    ) {
        return authService.signIn(loginRequestDto, clientInfo);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(JwtAuthenticationToken token, @RequestAttribute ClientInfoDto clientInfo) {
        return authService.refresh(token, clientInfo);
    }

    @PostMapping("/signOut")
    public ResponseEntity<SignOutResponseDto> signOut(JwtAuthenticationToken token, @RequestAttribute ClientInfoDto clientInfo) {
        return authService.signOut(token, clientInfo);
    }
}