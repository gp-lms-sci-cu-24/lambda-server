package com.cu.sci.lambdaserver.auth;

import com.cu.sci.lambdaserver.UserPackage.User;
import com.cu.sci.lambdaserver.UserPackage.UserRepository;
import com.cu.sci.lambdaserver.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .nationalId(request.getNationalId())
                .age(request.getAge())
                .password(passwordEncoder.encode(request.getPassword()))//encode the password before save it
                .build();
        repository.save(user);
        var jwtToken =jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getId(),
                        request.getPassword()
                )
        );
        var user =repository.findByuserId(request.getId()).orElseThrow();
        var jwtToken =jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
