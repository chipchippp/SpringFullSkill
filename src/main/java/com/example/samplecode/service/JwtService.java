package com.example.samplecode.service;

import com.example.samplecode.util.TokenType;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails user);
    String extractUsername(String token, TokenType tokenType);
    boolean isValid(String token, UserDetails userDetails, TokenType tokenType);

    String generateRefreshToken(UserDetails user);
}
