package com.example.samplecode.service.impl;

import com.example.samplecode.service.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String generateToken(UserDetails username) {
        return "token";
    }
}
