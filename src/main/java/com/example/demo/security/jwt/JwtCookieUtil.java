package com.example.demo.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class JwtCookieUtil {
    public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token"; // 나중 확장용

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
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(Duration.ofMinutes(15))
                .build();
    }
}
