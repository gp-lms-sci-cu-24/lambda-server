package com.cu.sci.lambdaserver.auth.service;

import com.cu.sci.lambdaserver.auth.dto.ClientInfoDto;
import com.cu.sci.lambdaserver.auth.dto.LoginRequestDto;
import com.cu.sci.lambdaserver.auth.dto.LoginResponseDto;
import com.cu.sci.lambdaserver.auth.dto.SignOutResponseDto;
import com.cu.sci.lambdaserver.auth.entities.RefreshToken;
import com.cu.sci.lambdaserver.auth.properties.SecurityConfigurationProperties;
import com.cu.sci.lambdaserver.auth.repositories.RefreshTokenRepository;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

/**
 * This class provides authentication services with cookie support.
 * It implements the IAuthService and IAuthSupportRefreshCookie interfaces.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthWithCookieService implements IAuthService, IAuthSupportRefreshCookie {

    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthenticationManager authenticationManager;

    private final IJwtTokenService jwtTokenService;

    private final PasswordEncoder passwordEncoder;

    private final IUserService userService;

    private final SecurityConfigurationProperties configurationProperties;


    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<LoginResponseDto> signIn(LoginRequestDto loginRequestDto, ClientInfoDto clientInfoDto) {
        log.info("login request: (username:{})", loginRequestDto.username());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.username(),
                        loginRequestDto.password()
                )
        );
        return signInAndAddCookie(authentication, clientInfoDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<LoginResponseDto> refresh(JwtAuthenticationToken jwtAuthenticationToken, ClientInfoDto clientInfo) {
        RefreshToken refreshToken = validateRefreshToken(jwtAuthenticationToken.getToken());
        refreshTokenRepository.delete(refreshToken);
        return signInAndAddCookie(jwtAuthenticationToken, clientInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<SignOutResponseDto> signOut(JwtAuthenticationToken jwtAuthenticationToken, ClientInfoDto clientInfo) {
        RefreshToken refreshToken = validateRefreshToken(jwtAuthenticationToken.getToken());
        refreshTokenRepository.delete(refreshToken);
        return ResponseEntity.ok(SignOutResponseDto.builder()
                .message("Signed out Successfully.")
                .build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<LoginResponseDto> signInAndAddCookie(Authentication authentication, ClientInfoDto clientInfoDto) {
        final Jwt access = jwtTokenService.generateAccessToken(authentication);
        final Jwt refresh = jwtTokenService.generateRefreshToken(authentication);
        final User user = userService.loadUserByUsername(authentication.getName());

        saveRefreshToken(refresh, clientInfoDto, user);
        ResponseCookie refreshCookie = generateRefreshCookie(refresh);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(LoginResponseDto.builder()
                        .accessToken(access.getTokenValue())
                        .tokenType(OAuth2AccessToken.TokenType.BEARER.getValue())
                        .expiredIn(Objects.requireNonNull(access.getExpiresAt()).getEpochSecond())
                        .build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseCookie generateRefreshCookie(Jwt refreshToken) {
        return ResponseCookie.from(configurationProperties.refreshCookie(), refreshToken.getTokenValue())
                .httpOnly(true)
                .path("/api/v1/auth")
                .secure(true)
                .maxAge(Objects.requireNonNull(refreshToken.getIssuedAt()).getEpochSecond())
                .build();
    }

    /**
     * This method is used to save the refresh token.
     *
     * @param refreshJwt    This is the first parameter to saveRefreshToken method which includes the refresh JWT.
     * @param clientInfoDto This is the second parameter to saveRefreshToken method which includes the client information.
     * @param user          This is the third parameter to saveRefreshToken method which includes the user information.
     */
    private void saveRefreshToken(Jwt refreshJwt, ClientInfoDto clientInfoDto, User user) {
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .uid(refreshJwt.getId())
                        .ipAddress(clientInfoDto.ipAddress())
                        .userAgent(clientInfoDto.userAgent())
                        .expiryDate(refreshJwt.getExpiresAt())
                        .user(user)
                        .token(passwordEncoder.encode(refreshJwt.getTokenValue()))
                        .build()
        );
    }

    /**
     * This method is used to validate the refresh token.
     *
     * @param jwt This is the first parameter to validateRefreshToken method which includes the JWT.
     * @return RefreshToken This returns the validated refresh token.
     * @throws ResponseStatusException If the refresh token is invalid.
     */
    private RefreshToken validateRefreshToken(Jwt jwt) {
        RefreshToken refreshToken = refreshTokenRepository.findById(jwt.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid refresh token"
                ));

        if (passwordEncoder.matches(jwt.getTokenValue(), refreshToken.getToken())) {
            return refreshToken;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid refresh token"
            );
        }
    }
}