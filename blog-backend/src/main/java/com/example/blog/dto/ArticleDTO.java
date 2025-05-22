package com.example.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private UUID id;
    private String title;
    private String content;
    private String author;
    private LocalDate publicationDate;
    private List<CommentDTO> comments;
}