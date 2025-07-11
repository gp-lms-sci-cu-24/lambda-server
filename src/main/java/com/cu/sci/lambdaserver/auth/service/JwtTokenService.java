package com.cu.sci.lambdaserver.auth.service;

import com.cu.sci.lambdaserver.auth.properties.SecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

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
     * @return A Jwt object representing the access token
     */
    @Override
    public Jwt generateAccessToken(Authentication authentication) {
        Instant now = Instant.now();

        String[] roles = getRoles(authentication);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(securityProperties.jwtAccess().expiredDuration()))
                .subject(authentication.getName())
                .claim("roles", roles)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims));
    }

    /**
     * This method generates a refresh token for the given authentication.
     * It creates a JWT claims set with the issuer, issued at time, expiration time, subject, and id,
     * and then encodes it into a JWT.
     *
     * @param authentication The Authentication object
     * @return A Jwt object representing the refresh token
     */
    @Override
    public Jwt generateRefreshToken(Authentication authentication) {
        Instant now = Instant.now();

        String[] roles = getRoles(authentication);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(securityProperties.jwtRefresh().expiredDuration()))
                .subject(authentication.getName())
                .id(UUID.randomUUID().toString())
                .claim("roles", roles)
                .build();
        return this.refreshEncoder.encode(JwtEncoderParameters.from(claims));
    }

    /**
     * This class is an implementation of the IAuthenticationFacade interface.
     * It provides methods to get the authentication and the authenticated user.
     * It is annotated with @Component to indicate that it's a Spring Bean.
     * It is annotated with @RequiredArgsConstructor to generate a constructor with required fields, in this case, IUserService.
     */
    private String[] getRoles(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::toString)
                    .map(s -> s.replace("ROLE_", ""))
                    .toArray(String[]::new);
        }

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::toString).toArray(String[]::new);
    }
}