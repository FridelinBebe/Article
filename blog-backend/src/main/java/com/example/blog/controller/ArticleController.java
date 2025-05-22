package com.example.blog.controller;

import com.example.blog.dto.ArticleCreateUpdateDTO;
import com.example.blog.dto.ArticleDTO;
import com.example.blog.dto.CommentCreateDTO;
import com.example.blog.dto.CommentDTO;
import com.example.blog.service.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable UUID id) {
        try {
            ArticleDTO article = articleService.getArticleById(id);
            return ResponseEntity.ok(article);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@Valid @RequestBody ArticleCreateUpdateDTO articleDto) {
        ArticleDTO createdArticle = articleService.createArticle(articleDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdArticle.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable UUID id, @Valid @RequestBody ArticleCreateUpdateDTO articleDto) {
        try {
            ArticleDTO updatedArticle = articleService.updateArticle(id, articleDto);
            return ResponseEntity.ok(updatedArticle);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable UUID id) {
        try {
            articleService.deleteArticle(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<CommentDTO> addCommentToArticle(
            @PathVariable UUID articleId,
            @Valid @RequestBody CommentCreateDTO commentDto) {
        try {
            CommentDTO createdComment = articleService.addCommentToArticle(articleId, commentDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{commentId}")
                    .buildAndExpand(createdComment.getId())
                    .toUri();
            return ResponseEntity.created(location).body(createdComment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable UUID articleId,
            @PathVariable UUID commentId) {
        try {
            articleService.deleteComment(articleId, commentId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}