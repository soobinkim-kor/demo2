package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "RT:";
    private static final long REFRESH_TOKEN_TTL = 7 * 24 * 60 * 60; // 7일

    public void save(String refreshToken, Long userId) {
        redisTemplate.opsForValue()
                .set(PREFIX + refreshToken, userId.toString(), REFRESH_TOKEN_TTL, TimeUnit.SECONDS);
    }

    public String getUserId(String refreshToken) {
        return redisTemplate.opsForValue().get(PREFIX + refreshToken);
    }

    public void delete(String refreshToken) {
        redisTemplate.delete(PREFIX + refreshToken);
    }

    public boolean exists(String refreshToken) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + refreshToken));
    }
}

