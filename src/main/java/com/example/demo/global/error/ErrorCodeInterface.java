package com.example.demo.global.error;

import org.springframework.http.HttpStatus;

public interface ErrorCodeInterface {

    String code();
    String messageKey();
    HttpStatus httpStatus();
}