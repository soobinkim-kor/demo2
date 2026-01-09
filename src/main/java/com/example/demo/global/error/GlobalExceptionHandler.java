package com.example.demo.global.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
        String message = messageSource.getMessage(
                e.getCommonErrorCode().messageKey(),
                null,
                locale
        );

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(
                        e.getCommonErrorCode(),
                        message,
                        request.getRequestURI()
                ));
    }

}
