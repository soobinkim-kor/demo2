package com.example.demo.dto.auth;

public record TokenPair(
        String accessToken,
        String refreshToken,
        Long usrNo,
        String usrId,
        String usrNm
) {}
