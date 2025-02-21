package com.example.samplecode.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// áp dụng cho cách 1, 2, 3 (implements WebMvcConfigurer)
//@Configuration
// áp dụng cho cách 4 (extends OncePerRequestFilter)
@Component
public class AppConfig extends OncePerRequestFilter {

    //    cach 4
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // Nếu là request OPTIONS (preflight), trả về ngay mà không đi tiếp
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        filterChain.doFilter(request, response);
    }
//    cach 1
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowCredentials(true)
//                .allowedOrigins("http://localhost:3000")
//                .allowedMethods("*")
//                .allowedHeaders("*");
//    }

//    cach 2
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowCredentials(true)
//                        .allowedOrigins("http://localhost:3000")
//                        .allowedMethods("*")
//                        .allowedHeaders("*");
//            }
//        };
//    }

//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
////        config.addAllowedOrigin("http://localhost:3000");
//        config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:3001"));
////        config.setAllowedMethods(List.of("*"));
////        config.setAllowedHeaders(List.of("*"));
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//
//        source.registerCorsConfiguration("/**", config);
//
//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return bean;
//    }

}
