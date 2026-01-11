package com.example.demo.request.article;

import com.example.demo.entity.ArticleEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleWriteRequest {
    private Long authorNo;
    private String title;
    private String content;

    public ArticleEntity toEntity() {
        return ArticleEntity.builder()
                .authorNo(1L)
                .title(title)
                .content(content)
                .build();
    }
}
