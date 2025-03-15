package com.example.samplecode.repository;

import com.example.samplecode.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByUsername(String username);
    Token findByAccessToken(String accessToken);
    Token findByRefreshToken(String refreshToken);
}
