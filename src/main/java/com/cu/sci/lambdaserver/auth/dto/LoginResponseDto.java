package com.cu.sci.lambdaserver.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * LoginResponseDto is a record class that represents the data transfer object for a login response.
 * It includes fields for the access token, token type, and expiration time, all of which are validated for null constraints.
 */
@Builder
public record LoginResponseDto(
        @JsonProperty("access_token")
        @NotNull
        String accessToken,

        @JsonProperty("token_type")
        @NotNull
        String tokenType,

        @JsonProperty("expired_in")
        @NotNull
        Long expiredIn
) {
}
