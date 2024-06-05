package com.cu.sci.lambdaserver.location.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUpdateLocationDto(
        @NotNull
        @NotBlank
        String name,

        @NotNull
        @NotBlank
        String path,

        String info,

        @NotNull
        @Min(0)
        Integer maxCapacity
) {
}
