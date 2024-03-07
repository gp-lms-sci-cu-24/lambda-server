package com.cu.sci.lambdaserver.auth;


import com.cu.sci.lambdaserver.UserPackage.User;
import com.cu.sci.lambdaserver.UserPackage.UserRepository;
import com.cu.sci.lambdaserver.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterRequest request){
        User user = new User();
        user.setId(request.getId());
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setRole(request.getRole());
        user.setLastname(request.getLastname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return repository.save(user);
    }
    //  public  AuthenticationResponse login(LoginRequest request){
//    authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(
//                    request.getEmail(), request.getEmail()
////                passwordEncoder.encode(request.getPassword())
//            )
//    );
//
//  }
    public ResponseEntity<String> signIn(AuthenticationRequest request) {
        System.err.println(request.getId());
        System.err.println(request.getPassword());
        Authentication authentication =   authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getId(), request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("user signed success!", HttpStatus.OK);
//    System.err.println(0);
//    var user = repository.findByEmail(request.getEmail())
//        .orElseThrow(()->new IllegalArgumentException("invalid email or password"));
//    System.err.println(1);
//
//    System.err.println(user.getEmail());
//
//    var jwtToken = jwtService.generateToken(user);
//    System.err.println(2);
//    var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
//    System.err.println(3);
//    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//    System.err.println(4);
//    authenticationResponse.setToken(jwtToken);
//    System.err.println(5);
//    authenticationResponse.setRefreshToken(refreshToken);
//    System.err.println(6);
//    return authenticationResponse;
    }

}
