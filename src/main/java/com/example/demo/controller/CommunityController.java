package com.example.demo.controller;

import com.example.demo.dto.article.ArticleDto;
import com.example.demo.entity.ArticleEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CommunityController {
    @GetMapping("/api/articles/writeArticle/")
    public ResponseEntity<Optional<ArticleEntity>> writeArticle(ArticleDto articleParam){
        // TODO
        return null;
    }
}
