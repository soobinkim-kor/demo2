package com.example.demo.controller;

import com.example.demo.dto.auth.TokenPair;
import com.example.demo.security.annotation.LoginRequired;
import com.example.demo.security.jwt.JwtCookieUtil;
import com.example.demo.request.user.LoginRequest;
import com.example.demo.response.user.TokenResponse;
import com.example.demo.service.AuthService;
import com.example.demo.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {

        // 1. 사용자 인증
        TokenPair tokenPair = authService.login(request);
        // 1️⃣ Authorization 헤더가 있으면 → API / 모바일
        String authHeader = httpRequest.getHeader("Authorization");
        // 1️⃣ API / 모바일
        if (authHeader != null) {
            return ResponseEntity.ok(
                    new TokenResponse(
                            tokenPair.accessToken(),
                            tokenPair.refreshToken()
                    )
            );
        }

        // 2️⃣ WEB (쿠키)
        ResponseCookie accessCookie =
                JwtCookieUtil.createAccessToken(
                        tokenPair.accessToken()
                );

        ResponseCookie refreshCookie =
                JwtCookieUtil.createRefreshToken(
                        tokenPair.refreshToken()
                );
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }

    @GetMapping("/authTest")
    @LoginRequired
    public ResponseEntity<?> authTest() {

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @LoginRequired
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue("refreshToken") String refreshToken
    ) {
        refreshTokenService.delete(refreshToken);

        ResponseCookie deleteAccess =
                JwtCookieUtil.deleteAccessToken();
        ResponseCookie deleteRefresh =
                JwtCookieUtil.deleteRefreshToken();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteAccess.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefresh.toString())
                .build();
    }
}
