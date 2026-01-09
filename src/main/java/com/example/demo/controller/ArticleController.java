package com.example.demo.controller;

import com.example.demo.request.article.ArticleWriteRequest;
import com.example.demo.response.article.ArticleWriteResponse;
import com.example.demo.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/api/articles/writeArticle/")
    public ResponseEntity<ArticleWriteResponse> writeArticle(ArticleWriteRequest articleWriteRequest){
        return null;
    }

    @GetMapping("/api/articles/getAllArticle/")
    public ResponseEntity<List<ArticleWriteResponse>> getAllArticle(ArticleWriteRequest articleWriteRequest){
        return new ResponseEntity<>(articleService.getAllArticle(), HttpStatus.OK);
    }
}
