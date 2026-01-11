package com.example.demo.request.article;

import com.example.demo.entity.ArticleEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleDeleteRequest {
    private Long articleId;

    public ArticleEntity toEntity(){
        return ArticleEntity
                .builder()
                .articleId(articleId)
                .build();
    }
}
