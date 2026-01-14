package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisTestService {

    private final RedisTemplate<String, String> redisTemplate;

    public void save() {
        redisTemplate.opsForValue().set("test:key", "hello redis");
    }

    public String get() {
        return redisTemplate.opsForValue().get("test:key");
    }

    public void delete() {
        redisTemplate.delete("test:key");
    }
}
