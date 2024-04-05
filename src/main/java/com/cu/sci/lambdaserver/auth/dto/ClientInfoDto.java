package com.cu.sci.lambdaserver.auth.dto;

import lombok.Builder;

/**
 * This class represents the response data transfer object for sign out operations.
 * It is a record class, which is a special kind of class in Java that is used to model immutable data.
 * It is annotated with @Builder to indicate that the builder pattern should be used to create instances of this class.
 */
@Builder
public record ClientInfoDto(
        String ipAddress,
        String userAgent
) {
}
