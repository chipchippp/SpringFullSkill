package com.example.samplecode.controller;

import com.example.samplecode.dto.request.SignInRequest;
import com.example.samplecode.dto.response.ResponseData;
import com.example.samplecode.dto.response.TokenResponse;
import com.example.samplecode.service.UserService;
import com.example.samplecode.service.impl.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@Tag(name = "Authentication Controller", description = "User API")
@Validated
public class AuthController {
    private final AuthService authService;

    @PostMapping("/access")
    public ResponseEntity<TokenResponse> login(@RequestBody SignInRequest request) {
        return new ResponseEntity<>(authService.authenticate(request), OK);
    }

//    @PostMapping("/refresh")
//    public ResponseEntity<TokenResponse> refresh(HttpServletRequest request) {
//        return new ResponseEntity<>(authService.refreshToken(request), OK);
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request) {
//        return new ResponseEntity<>(authService.logout(request), OK);
//    }


}
