package com.example.demo.response.article;

import com.example.demo.entity.ArticleEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleListResponse {

    private Long id;
    private String title;

    public static ArticleListResponse from(ArticleEntity entity) {
        return ArticleListResponse.builder()
                .id(entity.getArticleId())
                .title(entity.getTitle())
                .build();
    }

}
