package com.example.demo.global.error;

import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCodeInterface {
    NOT_FOUND("COMMON_001", "common.not.found", HttpStatus.NOT_FOUND),
    RESOURCE_NOT_FOUND("","",HttpStatus.NOT_FOUND);

    private final String code;
    private final String messageKey;
    private final HttpStatus status;

    CommonErrorCode(String code, String messageKey, HttpStatus status) {
        this.code = code;
        this.messageKey = messageKey;
        this.status = status;
    }

    @Override public String code() { return code; }
    @Override public String messageKey() { return messageKey; }
    @Override public HttpStatus httpStatus() { return status; }
}
