package com.example.demo.global.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(
            BusinessException e,
            HttpServletRequest request,
            Locale locale
    ){
        ErrorCodeInterface errorCode = e.getErrorCode();
        String message = messageSource.getMessage(
                errorCode.messageKey(),
                null,
                locale
        );

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(
                        errorCode,
                        message,
                        request.getRequestURI()
                ));
    }

}
