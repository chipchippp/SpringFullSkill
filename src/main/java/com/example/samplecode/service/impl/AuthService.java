package com.example.samplecode.service.impl;

import com.example.samplecode.dto.request.SignInRequest;
import com.example.samplecode.dto.response.TokenResponse;
import com.example.samplecode.repository.UserRepository;
import com.example.samplecode.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public TokenResponse authenticate(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findUserByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String accessToken = jwtService.generateToken(user);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken("refresh-token")
                .userId(user.getId())
                .build();
    }
}
