package com.cu.sci.lambdaserver.auth.service;

import org.springframework.security.core.Authentication;

/**
 * IJwtTokenService is an interface that defines the contract for JWT token services.
 * It includes methods for generating access and refresh tokens.
 */
public interface IJwtTokenService {

    /**
     * This method generates an access token for a given authentication.
     *
     * @param authentication The Authentication object representing the user's authentication
     * @return A string representing the generated access token
     */
    String generateAccessToken(Authentication authentication);

    /**
     * This method generates a refresh token for a given authentication.
     *
     * @param authentication The Authentication object representing the user's authentication
     * @return A string representing the generated refresh token
     */
    String generateRefreshToken(Authentication authentication);
}