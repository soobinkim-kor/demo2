package com.example.demo.global.error;

public enum ErrorCode {
    INVALID_CREDENTIALS("AUTH_001", "auth.invalid.credentials"),
    UNAUTHORIZED("AUTH_002", "auth.unauthorized"),
    USER_NOT_FOUND("USER_001", "user.not.found");

    private final String code;
    private final String messageKey;

    ErrorCode(String code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public String code() {
        return code;
    }

    public String messageKey() {
        return messageKey;
    }
}
