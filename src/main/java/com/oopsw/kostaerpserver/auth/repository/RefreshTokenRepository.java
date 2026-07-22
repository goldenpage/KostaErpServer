package com.oopsw.kostaerpserver.auth.repository;

import com.oopsw.kostaerpserver.auth.repository.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUsername(String username);
    Optional<RefreshToken> findByToken(String token);
    void deleteByUsername(String username);
}
