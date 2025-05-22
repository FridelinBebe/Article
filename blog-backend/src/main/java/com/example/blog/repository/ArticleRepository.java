// src/main/java/com/example/blog/repository/ArticleRepository.java
package com.example.blog.repository;

import com.example.blog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
}