package com.cu.sci.lambdaserver.utils.dto;

import lombok.Builder;

@Builder
public record MessageResponse(
        String message
) {
}
