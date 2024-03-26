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

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtEncoder encoder;
    @Qualifier("jwtRefreshEncoder")
    private final JwtEncoder refreshEncoder;
    private final SecurityConfigurationProperties securityProperties;

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
