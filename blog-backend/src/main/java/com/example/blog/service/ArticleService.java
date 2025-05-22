package com.example.blog.service;

import com.example.blog.dto.ArticleCreateUpdateDTO;
import com.example.blog.dto.ArticleDTO;
import com.example.blog.dto.CommentCreateDTO;
import com.example.blog.dto.CommentDTO;
import com.example.blog.model.Article;
import com.example.blog.model.Comment;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ArticleDTO getArticleById(UUID id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with ID: " + id));
        return convertToDto(article);
    }

    @Transactional
    public ArticleDTO createArticle(ArticleCreateUpdateDTO articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setAuthor(articleDto.getAuthor());
        article.setPublicationDate(LocalDate.now());
        Article savedArticle = articleRepository.save(article);
        return convertToDto(savedArticle);
    }

    @Transactional
    public ArticleDTO updateArticle(UUID id, ArticleCreateUpdateDTO articleDto) {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with ID: " + id));

        existingArticle.setTitle(articleDto.getTitle());
        existingArticle.setContent(articleDto.getContent());
        existingArticle.setAuthor(articleDto.getAuthor());

        Article updatedArticle = articleRepository.save(existingArticle);
        return convertToDto(updatedArticle);
    }

    @Transactional
    public void deleteArticle(UUID id) {
        if (!articleRepository.existsById(id)) {
            throw new EntityNotFoundException("Article not found with ID: " + id);
        }
        articleRepository.deleteById(id);
    }

    @Transactional
    public CommentDTO addCommentToArticle(UUID articleId, CommentCreateDTO commentDto) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with ID: " + articleId));

        Comment comment = new Comment();
        comment.setAuthor(commentDto.getAuthor());
        comment.setText(commentDto.getText());
        comment.setArticle(article);

        Comment savedComment = commentRepository.save(comment);
        article.getComments().add(savedComment);

        return convertToDto(savedComment);
    }

    @Transactional
    public void deleteComment(UUID articleId, UUID commentId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with ID: " + articleId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + commentId));

        if (!comment.getArticle().getId().equals(articleId)) {
            throw new IllegalArgumentException("Comment with ID " + commentId + " does not belong to article with ID " + articleId);
        }

        article.getComments().remove(comment);
        commentRepository.delete(comment);
    }

    // --- Helper methods for DTO conversion ---
    private ArticleDTO convertToDto(Article article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setAuthor(article.getAuthor());
        dto.setPublicationDate(article.getPublicationDate());
        if (article.getComments() != null) {
            dto.setComments(article.getComments().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList()));
        } else {
            dto.setComments(List.of());
        }
        return dto;
    }

    private CommentDTO convertToDto(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setAuthor(comment.getAuthor());
        dto.setText(comment.getText());
        return dto;
    }
}