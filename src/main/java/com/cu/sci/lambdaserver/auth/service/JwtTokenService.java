package com.cu.sci.lambdaserver.auth.service;

import com.cu.sci.lambdaserver.auth.config.SecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * JwtTokenService is a service class that implements the IJwtTokenService interface.
 * It provides methods for generating JWT access and refresh tokens.
 */
@Service
@RequiredArgsConstructor
public class JwtTokenService implements IJwtTokenService {
    // The JWT encoder for encoding JWTs
    private final JwtEncoder encoder;
    // The JWT encoder for encoding refresh JWTs
    @Qualifier("jwtRefreshEncoder")
    private final JwtEncoder refreshEncoder;
    // The security configuration properties
    private final SecurityConfigurationProperties securityProperties;

    /**
     * This method generates an access token for the given authentication.
     * It creates a JWT claims set with the issuer, issued at time, expiration time, subject, and scope,
     * and then encodes it into a JWT.
     *
     * @param authentication The Authentication object
     * @return A string representing the access token
     */
    @Override
    public String generateAccessToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(securityProperties.jwtAccess().expiredDuration()))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * This method generates a refresh token for the given authentication.
     * It creates a JWT claims set with the issuer, issued at time, expiration time, subject, and id,
     * and then encodes it into a JWT.
     *
     * @param authentication The Authentication object
     * @return A string representing the refresh token
     */
    @Override
    public String generateRefreshToken(Authentication authentication) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(securityProperties.jwtRefresh().expiredDuration()))
                .subject(authentication.getName())
                .id(UUID.randomUUID().toString())
                .build();
        return this.refreshEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}