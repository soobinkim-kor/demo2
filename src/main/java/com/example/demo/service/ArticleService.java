package com.example.demo.service;

import com.example.demo.entity.ArticleEntity;
import com.example.demo.repository.ArticleBaseRepository;
import com.example.demo.response.article.ArticleWriteResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleBaseRepository articleBaseRepository;

    public List<ArticleWriteResponse> getAllArticle() {
        List<ArticleEntity> allArticle = articleBaseRepository.findAll();

        if (CollectionUtils.isEmpty(allArticle)) {
//            throw new BusinessException(ArticleErrorCode.ARTICLE_NOT_FOUND);
        }

        return allArticle.stream()
                .map(ArticleWriteResponse::from)
                .toList();
    }
}

