package com.example.demo.global.error;

import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCodeInterface {
    INVALID_CREDENTIALS("AUTH_001", "auth.invalid.credentials", HttpStatus.NOT_ACCEPTABLE),
    UNAUTHORIZED("AUTH_002", "auth.unauthorized", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("USER_001", "user.not.found", HttpStatus.NOT_FOUND);

    private final String code;
    private final String messageKey;
    private final HttpStatus status;

    AuthErrorCode(String code, String messageKey, HttpStatus status) {
        this.code = code;
        this.messageKey = messageKey;
        this.status = status;
    }

    @Override public String code() { return code; }
    @Override public String messageKey() { return messageKey; }
    @Override public HttpStatus httpStatus() { return status; }
}
