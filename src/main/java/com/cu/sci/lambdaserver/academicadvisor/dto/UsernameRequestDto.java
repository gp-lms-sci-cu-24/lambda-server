package com.cu.sci.lambdaserver.academicadvisor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsernameRequestDto(
        @NotNull
        @NotBlank
        String username
) {
}
