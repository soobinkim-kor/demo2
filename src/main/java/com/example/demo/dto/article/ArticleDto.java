package com.example.demo.dto.article;

import com.example.demo.entity.ArticleEntity;

import java.time.LocalDateTime;

public class ArticleDto {

    private Long articleId;
    private String title;
    private String content;

    private int viewCount;
    private int commentCount;

    private LocalDateTime createdAt;

    // 생성자 private
    private ArticleDto(
            Long articleId,
            String title,
            String content,
            int viewCount,
            int commentCount,
            LocalDateTime createdAt
    ) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
    }

    // 정적 팩토리 메서드 ⭐⭐⭐
    public static ArticleDto from(ArticleEntity base) {
        return new ArticleDto(
                base.getArticleId(),
                base.getTitle(),
                base.getContent(),
                base.getViewCount(),
                base.getCommentCount(),
                base.getCreatedAt()
        );
    }

    // getter
}
