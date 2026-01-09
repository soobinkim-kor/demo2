package com.example.demo.global.error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final CommonErrorCode commonErrorCode;

    public BusinessException(CommonErrorCode commonErrorCode) {
        this.commonErrorCode = commonErrorCode;
    }
}
