package com.example.samplecode.service;

import com.example.samplecode.exception.ResourceNotFoundException;
import com.example.samplecode.model.RedisToken;
import com.example.samplecode.repository.RedisTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisTokenService {
    private final RedisTokenRepository redisTokenRepository;

    public String saveToken(RedisToken redisToken) {
        RedisToken result = redisTokenRepository.save(redisToken);
        return result.getId();
    }

    public void deleteToken(String id) {
        redisTokenRepository.deleteById(id);
    }

    public RedisToken getTokenById(String id) {
        return redisTokenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    }
}
