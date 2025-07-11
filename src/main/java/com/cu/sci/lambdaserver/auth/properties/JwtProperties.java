package com.cu.sci.lambdaserver.auth.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.time.DurationMin;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

/**
 * JwtProperties is a record class that holds the properties for a JWT.
 * It includes the RSA public key, RSA private key, and the expired duration.
 */
public record JwtProperties(
        @NotNull
        @NotBlank
        RSAPublicKey rsaPublicKey,

        @NotNull
        @NotBlank
        RSAPrivateKey rsaPrivateKey,

        @NotNull
        @NotBlank
        @DurationMin(seconds = 30)
        Duration expiredDuration
) {
}