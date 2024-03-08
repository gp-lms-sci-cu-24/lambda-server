package com.cu.sci.lambdaserver.auth;

import com.cu.sci.lambdaserver.UserPackage.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
//  @PostMapping("/authenticate")
//  public ResponseEntity<AuthenticationResponse> authenticate(
//      @RequestBody AuthenticationRequest request
//  ) {
//    return ResponseEntity.ok(service.authenticate(request));
//  }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(
            @RequestBody AuthenticationRequest request
    ) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getId(),request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(authentication.getName(), HttpStatus.OK);
    }
}
