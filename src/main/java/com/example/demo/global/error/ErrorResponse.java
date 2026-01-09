package com.example.demo.global.error;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private final LocalDateTime timestamp;
    private final String path;

    private ErrorResponse(String code, String message, String path) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

    public static ErrorResponse of(CommonErrorCode commonErrorCode, String message, String path) {
        return new ErrorResponse(commonErrorCode.code(), message, path);
    }
}
