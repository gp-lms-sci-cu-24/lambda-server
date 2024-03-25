package com.cu.sci.lambdaserver.auth.config;


import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "security")
public record SecurityConfigurationProperties(
        @NotNull
        @NestedConfigurationProperty
        JwtProperties jwtAccess,
        @NotNull
        @NestedConfigurationProperty
        JwtProperties jwtRefresh
        ) {
}
