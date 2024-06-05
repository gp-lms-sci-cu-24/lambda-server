package com.cu.sci.lambdaserver.storage.cloudinary.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.util.Map;

@ConfigurationProperties(prefix = "cloudinary")
public record CloudinaryConfigProperties(
        @NotNull
        String cloudName,
        @NotNull
        String apiKey,
        @NotNull
        String apiSecret,
        DataSize maxImageSize
) {
    public Map<String, String> toMap() {
        return Map.of(
                "cloud_name", cloudName(),
                "api_key", apiKey(),
                "api_secret", apiSecret(),
                "secure", "true"
        );
    }
}
