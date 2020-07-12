package com.example.demojpa.blog.api;

import com.example.demojpa.blog.api.dto.PostDto;
import com.example.demojpa.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long postArticle(@RequestBody PostDto createPostDto) {
        return postService.postArticle(createPostDto);
    }
}
