package com.example.demo.controller;

import com.example.demo.service.RedisTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/redis")
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class RedisTestController {

    private final RedisTestService redisTestService;

    @GetMapping("/save")
    public String save() {
        redisTestService.save();
        return "saved";
    }

    @GetMapping("/get")
    public String get() {
        return redisTestService.get();
    }

    @GetMapping("/delete")
    public String delete() {
        redisTestService.delete();
        return "deleted";
    }
}
