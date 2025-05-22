package com.example.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDTO {
    @NotBlank(message = "Author cannot be empty")
    @Size(max = 100, message = "Author name must be less than 100 characters")
    private String author;

    @NotBlank(message = "Comment text cannot be empty")
    private String text;
}