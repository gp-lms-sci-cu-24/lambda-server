package com.cu.sci.lambdaserver.auth.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.time.DurationMin;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

public record JwtProperties (
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
