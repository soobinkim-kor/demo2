package com.example.demo.global.error;

import org.springframework.http.HttpStatus;

public enum ArticleErrorCode implements ErrorCodeInterface {

    ARTICLE_NOT_FOUND("ARTICLE_001", "article.not.found", HttpStatus.NOT_FOUND),
    ARTICLE_FORBIDDEN("ARTICLE_002", "article.forbidden", HttpStatus.FORBIDDEN);

    private final String code;
    private final String messageKey;
    private final HttpStatus status;

    ArticleErrorCode(String code, String messageKey, HttpStatus status) {
        this.code = code;
        this.messageKey = messageKey;
        this.status = status;
    }

    @Override public String code() { return code; }
    @Override public String messageKey() { return messageKey; }
    @Override public HttpStatus httpStatus() { return status; }
}

