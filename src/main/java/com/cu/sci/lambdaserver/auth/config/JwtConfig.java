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
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;

/**
 * JwtConfig is a configuration class that provides beans for JWT encoding and decoding.
 */
@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    private static final String AUTHORITY_PREFIX = "ROLE_";

    private static final String CLAIM_ROLES = "roles";

    // Security configuration properties
    private final SecurityConfigurationProperties securityProperties;

    /**
     * Provides a JwtDecoder bean for decoding JWTs with the public key from the security properties.
     *
     * @return a JwtDecoder
     */
    @Bean
    @Primary
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(securityProperties.jwtAccess().rsaPublicKey()).build();
    }

    /**
     * Provides a JwtEncoder bean for encoding JWTs with the private key from the security properties.
     *
     * @return a JwtEncoder
     */
    @Bean
    @Primary
    public JwtEncoder jwtEncoder() {
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
    public JwtDecoder jwtRefreshDecoder() {
        return NimbusJwtDecoder.withPublicKey(securityProperties.jwtRefresh().rsaPublicKey()).build();
    }

    /**
     * Provides a JwtEncoder bean for encoding refresh JWTs with the private key from the security properties.
     *
     * @return a JwtEncoder
     */
    @Bean
    @Qualifier("jwtRefreshEncoder")
    public JwtEncoder jwtRefreshEncoder() {
        JWK jwk = new RSAKey
                .Builder(securityProperties.jwtRefresh().rsaPublicKey())
                .privateKey(securityProperties.jwtRefresh().rsaPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }


    /**
     * This method provides a converter for JWT to AbstractAuthenticationToken.
     * It sets the JwtGrantedAuthoritiesConverter to the JwtAuthenticationConverter.
     *
     * @return Converter<Jwt, AbstractAuthenticationToken> This returns a converter for JWT to AbstractAuthenticationToken.
     */
    @Bean
    protected Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(getJwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }

    /**
     * This method provides a converter for JWT to a collection of GrantedAuthority.
     * It sets the authority prefix and authorities claim name to the JwtGrantedAuthoritiesConverter.
     *
     * @return Converter<Jwt, Collection < GrantedAuthority>> This returns a converter for JWT to a collection of GrantedAuthority.
     */
    @Bean
    protected Converter<Jwt, Collection<GrantedAuthority>> getJwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix(AUTHORITY_PREFIX);
        converter.setAuthoritiesClaimName(CLAIM_ROLES);
        return converter;
    }
}