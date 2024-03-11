package com.cu.sci.lambdaserver.auth;

import com.cu.sci.lambdaserver.UserPackage.User;
import com.cu.sci.lambdaserver.UserPackage.UserRepository;
import com.cu.sci.lambdaserver.config.JwtService;
import com.cu.sci.lambdaserver.validator.ObjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final ObjectValidator validator;

    public Object register(RegisterRequest request) {
        var violations = validator.validate(request);

        if(!violations.isEmpty()){
            return violations.stream().collect(Collectors.joining("\n"));
        }

        // Check if the user ID already exists in the database
        if (repository.existsById(Long.parseLong(request.getId()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with ID " + request.getId() + " already exists");
        }
        // Create a new user and save it
        User user = new User();
        user.setId(request.getId());
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setRole(request.getRole());
        user.setLastname(request.getLastname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);
    }
    public ResponseEntity<Object> signIn(LoginRequest request) {
        var violations = validator.validate(request);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(violations.stream().collect(Collectors.joining("\n")));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getId(), request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), userDetails);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        return ResponseEntity.ok(tokens);
    }

}
