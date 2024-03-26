package com.cu.sci.lambdaserver.auth.config;


import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * SecurityConfigurationProperties is a record class that holds the properties for security configuration.
 * It includes the properties for JWT access and JWT refresh.
 */
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
