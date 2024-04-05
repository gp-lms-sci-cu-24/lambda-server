package com.cu.sci.lambdaserver.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * IJwtTokenService is an interface that defines the contract for JWT token services.
 * It includes methods for generating access and refresh tokens.
 * Each token is represented as a Jwt object.
 */
public interface IJwtTokenService {

    /**
     * This method generates an access token for a given authentication.
     * The generated token is represented as a Jwt object.
     *
     * @param authentication The Authentication object representing the user's authentication
     * @return A Jwt object representing the generated access token
     */
    Jwt generateAccessToken(Authentication authentication);

    /**
     * This method generates a refresh token for a given authentication.
     * The generated token is represented as a Jwt object.
     *
     * @param authentication The Authentication object representing the user's authentication
     * @return A Jwt object representing the generated refresh token
     */
    Jwt generateRefreshToken(Authentication authentication);
}