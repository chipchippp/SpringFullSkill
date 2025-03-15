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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public TokenResponse accessToken(SignInRequest signInRequest) {
        log.info("---------- authenticate ----------");

        var user = userService.getByUsername(signInRequest.getUsername());
        if (!user.isEnabled()) {
            throw new InvalidDataException("User not active");
        }

        List<String> roles = userService.getAllRolesByUserId(user.getId());
        List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getUsername(),
                        signInRequest.getPassword(),
                        authorities));

        // create new access token
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        String resetToken = jwtService.generateResetToken(user);

        // save token to db
        tokenService.saveToken(Token.builder()
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .resetToken(resetToken)
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

    public String forgotPassword(String email) {
        // check email in database
        User user = userService.getUserByEmail(email);
        // user is active or inactive
        if (!user.isEnabled()) {
            throw new UsernameNotFoundException("User not active");
        }
        // generate reset password token
        String resetToken = jwtService.generateResetToken(user);

        // send email to user
        String confirmLink = String.format("curl --location --request POST 'localhost:8888/api/v1/auth/reset-password' \\\n" +
                "--header 'Accept: */*' \\\n" +
                "--header 'Content-Type: application/json;charset=UTF-8' \\\n" +
                "--data '%s'", resetToken);
        log.info("Reset password link: {}", confirmLink);
        return "Email sent";
    }

    public String resetPassword(String secretKey) {
        log.info("reset password token: {}", secretKey);

        final String username = jwtService.extractUsername(secretKey, RESET_TOKEN);
        var user = userService.getByUsername(username);
        if (!jwtService.isValid(secretKey, user, RESET_TOKEN)) {
            throw new InvalidDataException("Token is invalid");
        }
        return "Password reset";
    }

    public String changePassword(ResetPasswordDTO request) {
        User user = validateToken(request.getSecretKey());
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidDataException("Password not match");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.saveUser(user);
        return "Password changed";
    }

    private User validateToken(String token) {
        // validate token
        var userName = jwtService.extractUsername(token, RESET_TOKEN);

        // validate user is active or not
        var user = userService.getByUsername(userName);
        if (!user.isEnabled()) {
            throw new InvalidDataException("User not active");
        }

        return user;
    }
}
