package com.example.demo.controller;

import com.example.demo.dto.user.UserSession;
import com.example.demo.request.article.ArticleDeleteRequest;
import com.example.demo.request.article.ArticleGetRequest;
import com.example.demo.request.article.ArticleWriteRequest;
import com.example.demo.response.article.ArticleWriteResponse;
import com.example.demo.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/api/articles/writeArticle/")
    public ResponseEntity<ArticleWriteResponse> writeArticle(@RequestBody ArticleWriteRequest articleWriteRequest){
        return new ResponseEntity<>(articleService.writeArticle(articleWriteRequest), HttpStatus.OK);
    }

    @GetMapping("/api/articles/getAllArticle/")
    public ResponseEntity<List<ArticleWriteResponse>> getAllArticle(ArticleGetRequest articleGetRequest){
        return new ResponseEntity<>(articleService.getAllArticle(), HttpStatus.OK);
    }

    @DeleteMapping("api/articles/deleteArticle/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId,
                                                              HttpSession session){
        UserSession userSession = (UserSession) session.getAttribute("LOGIN_USER");

        Long loginUserNo = userSession.userNo();

        articleService.deleteArticle(articleId, loginUserNo);

        return ResponseEntity.noContent().build();
    }
}
