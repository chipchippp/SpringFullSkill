package com.example.samplecode.configuration;

import com.example.samplecode.service.JwtService;
import com.example.samplecode.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PreFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("PreFilter: " + request.getMethod() + " " + request.getRequestURI());
        final String authorization = request.getHeader("Authorization");
//        log.info("Authorization: " + authorization);
        if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Cho phép request tiếp tục đi qua filter chain
            return;
        }
        final String token = authorization.substring(7);
//        log.info("Token: " + token);
        final String username = jwtService.extractUsername(token);

        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.getUserDetailsService().loadUserByUsername(username);
            if (jwtService.isValid(token, userDetails)){
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            } else {
                log.info("Token is invalid");
            }
        }

        filterChain.doFilter(request, response); // Cho phép request tiếp tục đi qua filter chain
    }
}
