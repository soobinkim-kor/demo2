package com.example.demo.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class JwtCookieUtil {
    private static final String ACCESS_TOKEN_COOKIE_NAME = "accessToken";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    // ⏰ 쿠키 만료 시간 (JWT 만료 시간과 맞추는 게 중요)
    private static final Duration ACCESS_TOKEN_MAX_AGE = Duration.ofMinutes(15);
    private static final Duration REFRESH_TOKEN_MAX_AGE = Duration.ofDays(7);

    private JwtCookieUtil() {}

    public static String extractAccessToken(HttpServletRequest request) {
        return extractCookieValue(request, ACCESS_TOKEN_COOKIE_NAME);
    }

    public static String extractRefreshToken(HttpServletRequest request) {
        return extractCookieValue(request, REFRESH_TOKEN_COOKIE_NAME);
    }

    private static String extractCookieValue(HttpServletRequest request, String cookieName) {
        if (request == null) return null;

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) return null;

        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                String value = cookie.getValue();
                return (value == null || value.isBlank()) ? null : value.trim();
            }
        }
        return null;
    }

    public static ResponseCookie createAccessToken(String token) {
        return ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(Duration.ofMinutes(15))
                .build();
    }

    /**
     * ✅ Refresh Token 쿠키 생성
     * - JS 접근 절대 불가
     * - 재발급 API에서만 사용
     */
    public static ResponseCookie createRefreshToken(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .httpOnly(true)           // 🔥 핵심
                .secure(true)
                .path("/api/auth")        // 재발급/로그아웃 API만
                .maxAge(REFRESH_TOKEN_MAX_AGE)
                .sameSite("Strict")
                .build();
    }

    /**
     * ✅ Access Token 쿠키 삭제
     */
    public static ResponseCookie deleteAccessToken() {
        return ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, "")
                .path("/")
                .maxAge(0)
                .build();
    }

    /**
     * ✅ Refresh Token 쿠키 삭제
     */
    public static ResponseCookie deleteRefreshToken() {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, "")
                .path("/api/auth")
                .maxAge(0)
                .build();
    }
}
