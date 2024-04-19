package com.cu.sci.lambdaserver.auth.repositories;

import com.cu.sci.lambdaserver.auth.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}