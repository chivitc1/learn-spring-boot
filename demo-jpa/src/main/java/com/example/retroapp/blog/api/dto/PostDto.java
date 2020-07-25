package com.example.retroapp.blog.api.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostDto {
    private String title;
    private String content;
    private String author;
}
