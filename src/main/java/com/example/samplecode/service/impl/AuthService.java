package com.example.samplecode.service.impl;

import com.example.samplecode.dto.request.ResetPasswordDTO;
import com.example.samplecode.dto.request.SignInRequest;
import com.example.samplecode.dto.response.TokenResponse;
import com.example.samplecode.exception.InvalidDataException;
import com.example.samplecode.model.Token;
import com.example.samplecode.model.User;
import com.example.samplecode.repository.UserRepository;
import com.example.samplecode.service.JwtService;
import com.example.samplecode.service.TokenService;
import com.example.samplecode.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.samplecode.util.TokenType.*;
import static org.springframework.http.HttpHeaders.REFERER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;

    public TokenResponse accessToken(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userService.getByUsername(request.getUsername());

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

//        save token into database
        tokenService.saveToken(Token.builder()
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public TokenResponse refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(REFERER);
        if (StringUtils.isBlank(refreshToken)) {
            throw new InvalidDataException("Token must not be blank");
        }
//        extract username from token
        final String username = jwtService.extractUsername(refreshToken, REFRESH_TOKEN);
//        check if token into database
        var user = userService.getByUsername(username);

        if (!jwtService.isValid(refreshToken, user, REFRESH_TOKEN)) {
            throw new InvalidDataException("Token is invalid");
        }
        String accessToken = jwtService.generateToken(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public String removeToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(REFERER);
        if (StringUtils.isBlank(refreshToken)) {
            throw new InvalidDataException("Token must not be blank");
        }
        //        extract username from token
        final String username = jwtService.extractUsername(refreshToken, ACCESS_TOKEN);

        Token currentToken = tokenService.getByUsername(username);

        tokenService.deleteToken(currentToken);

        return "Logout success";
    }

}
