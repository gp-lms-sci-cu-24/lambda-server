package com.cu.sci.lambdaserver.auth.properties;


import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * This class holds the properties for security configuration.
 * It includes the properties for JWT access and JWT refresh.
 * It is annotated with @ConfigurationProperties to indicate that it's a source of configuration properties.
 * The prefix "security" is used to map the properties.
 */
@ConfigurationProperties(prefix = "security")
public record SecurityConfigurationProperties(
        @NotNull
        @NestedConfigurationProperty
        JwtProperties jwtAccess,
        @NotNull
        @NestedConfigurationProperty
        JwtProperties jwtRefresh,

        @DefaultValue("refresh_auth")
        String refreshCookie
) {
}
