package com.example.demo.service;

import com.example.demo.dto.article.ArticleDto;
import com.example.demo.entity.ArticleEntity;
import com.example.demo.repository.ArticleBaseRepository;
import com.example.demo.response.article.ArticleWriteResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleBaseRepository articleBaseRepository;

    public ArticleWriteResponse getArticle(Long articleId) {
        ArticleEntity article = articleBaseRepository.findByArticleId(articleId).orElseThrow(() -> new RuntimeException(""));

        if (article == null) {
//            throw new BusinessException(ArticleErrorCode.ARTICLE_NOT_FOUND);
        }

        return ArticleWriteResponse.from(article);
    }
}

