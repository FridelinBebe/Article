package com.example.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateUpdateDTO {
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotBlank(message = "Author cannot be empty")
    @Size(max = 100, message = "Author name must be less than 100 characters")
    private String author;
}