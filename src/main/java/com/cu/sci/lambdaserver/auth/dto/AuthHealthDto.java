package com.cu.sci.lambdaserver.auth.dto;

import com.cu.sci.lambdaserver.utils.enums.Role;
import lombok.Builder;

import java.util.Collection;

@Builder
public record AuthHealthDto(
        String message,
        Collection<Role> roles
) {
}
