package com.example.demo.controller;

import com.example.demo.security.jwt.JwtCookieUtil;
import com.example.demo.request.user.LoginRequest;
import com.example.demo.response.user.TokenResponse;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {

        // 1. 사용자 인증
        String accessToken = authService.login(request);
// 1️⃣ Authorization 헤더가 있으면 → API / 모바일
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null) {
            return ResponseEntity.ok(new TokenResponse(accessToken));
        }

        // 2️⃣ 없으면 → 웹 (쿠키)
        ResponseCookie cookie = JwtCookieUtil.createAccessToken(accessToken);
        log.info("cookie {}",cookie);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
