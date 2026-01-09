package com.example.demo.response.article;

import com.example.demo.entity.ArticleEntity;

import java.time.LocalDateTime;

public record ArticleWriteResponse(Long articleId, LocalDateTime createdAt) {

    public static ArticleWriteResponse from(ArticleEntity article) {
        return new ArticleWriteResponse(
                article.getArticleId(),
                article.getCreatedAt()
        );
    }
}
