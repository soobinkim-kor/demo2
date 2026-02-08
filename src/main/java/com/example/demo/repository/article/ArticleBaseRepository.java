package com.example.demo.repository.article;

import com.example.demo.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleBaseRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByArticleId(Long articleId);
    List<ArticleEntity> findAll();
}
