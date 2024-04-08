package com.cu.sci.lambdaserver.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * LoginRequestDto is a record class that represents the data transfer object for a login request.
 * It includes fields for the username and password, both of which are validated for null, blank, and length constraints.
 */
public record LoginRequestDto(
        @NotNull
        @NotBlank
        @Length(min = 3, max = 50)
        String username,
        @NotNull
        @NotBlank
        @Length(min = 3, max = 50)
        String password
) {
}
