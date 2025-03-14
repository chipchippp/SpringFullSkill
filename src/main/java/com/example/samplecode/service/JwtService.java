package com.example.samplecode.service;

import com.example.samplecode.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails user);
    String extractUsername(String token);
    boolean isValid(String token, UserDetails userDetails);

    String generateRefreshToken(UserDetails user);
}
