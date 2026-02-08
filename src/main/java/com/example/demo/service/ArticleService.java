package com.example.demo.service;

import com.example.demo.entity.ArticleEntity;
import com.example.demo.global.error.ArticleErrorCode;
import com.example.demo.global.error.BusinessException;
import com.example.demo.repository.article.ArticleBaseRepository;
import com.example.demo.request.article.ArticleWriteRequest;
import com.example.demo.response.article.ArticleWriteResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleBaseRepository articleBaseRepository;

    public List<ArticleWriteResponse> getAllArticle() {
        List<ArticleEntity> allArticle = articleBaseRepository.findAll();

        return allArticle.stream()
                .map(ArticleWriteResponse::from)
                .toList();
    }

    public ArticleWriteResponse writeArticle(ArticleWriteRequest articleWriteRequest) {
        ArticleEntity newArticle = articleWriteRequest.toEntity();

        ArticleEntity saved = articleBaseRepository.save(newArticle);
        return ArticleWriteResponse.from(saved);
    }

    public void deleteArticle(Long articleId, Long loginUserNo){
        ArticleEntity article = articleBaseRepository.findById(articleId)
                .orElseThrow(() -> new BusinessException(ArticleErrorCode.ARTICLE_NOT_FOUND));
        if (!article.getAuthorNo().equals(loginUserNo)) {
            throw new BusinessException(ArticleErrorCode.ARTICLE_FORBIDDEN);
        }
        articleBaseRepository.delete(article);
    }
}

