package com.example.demo.jwt;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class JwtCookieUtil {

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
