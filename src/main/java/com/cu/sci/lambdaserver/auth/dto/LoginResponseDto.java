package com.cu.sci.lambdaserver.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginResponseDto(
        @JsonProperty("access_token")
        @NotNull
        String accessToken,

        @JsonProperty("refresh_token")
        @NotNull
        String refreshToken
) {
}
