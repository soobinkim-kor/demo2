package com.example.demo.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorCodeInterface errorCode;

    public BusinessException(ErrorCodeInterface errorCode) {
        super(errorCode.messageKey());
        this.errorCode = errorCode;
    }
}