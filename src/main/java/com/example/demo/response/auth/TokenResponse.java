package com.example.demo.response.auth;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {}
