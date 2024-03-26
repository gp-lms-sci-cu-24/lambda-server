package com.cu.sci.lambdaserver.auth.config;

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

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final SecurityConfigurationProperties securityProperties;

    @Bean
    @Primary
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(securityProperties.jwtAccess().rsaPublicKey()).build();
    }

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

    @Bean
    @Qualifier("jwtRefreshDecoder")
    public JwtDecoder jwtRefreshDecoder(){
        return NimbusJwtDecoder.withPublicKey(securityProperties.jwtRefresh().rsaPublicKey()).build();
    }

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
