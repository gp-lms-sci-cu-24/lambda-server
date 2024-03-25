package com.cu.sci.lambdaserver.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record LoginRequestDto(
        @NotNull
        @NotBlank
        @Length(min =3, max =50)
        String username,
        @NotNull
        @NotBlank
        @Length(min = 5, max =50)
        String password
) {
}
