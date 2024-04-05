package com.cu.sci.lambdaserver.auth.config;

import com.cu.sci.lambdaserver.auth.properties.SecurityConfigurationProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

/**
 * JwtConfig is a configuration class that provides beans for JWT encoding and decoding.
 */
@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    // Security configuration properties
    private final SecurityConfigurationProperties securityProperties;

    /**
     * Provides a JwtDecoder bean for decoding JWTs with the public key from the security properties.
     *
     * @return a JwtDecoder
     */
    @Bean
    @Primary
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(securityProperties.jwtAccess().rsaPublicKey()).build();
    }

    /**
     * Provides a JwtEncoder bean for encoding JWTs with the private key from the security properties.
     *
     * @return a JwtEncoder
     */
    @Bean
    @Primary
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey
                .Builder(securityProperties.jwtAccess().rsaPublicKey())
                .privateKey(securityProperties.jwtAccess().rsaPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * Provides a JwtDecoder bean for decoding refresh JWTs with the public key from the security properties.
     *
     * @return a JwtDecoder
     */
    @Bean
    @Qualifier("jwtRefreshDecoder")
    public JwtDecoder jwtRefreshDecoder(){
        return NimbusJwtDecoder.withPublicKey(securityProperties.jwtRefresh().rsaPublicKey()).build();
    }

    /**
     * Provides a JwtEncoder bean for encoding refresh JWTs with the private key from the security properties.
     *
     * @return a JwtEncoder
     */
    @Bean
    @Qualifier("jwtRefreshEncoder")
    public JwtEncoder jwtRefreshEncoder(){
        JWK jwk = new RSAKey
                .Builder(securityProperties.jwtRefresh().rsaPublicKey())
                .privateKey(securityProperties.jwtRefresh().rsaPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

}