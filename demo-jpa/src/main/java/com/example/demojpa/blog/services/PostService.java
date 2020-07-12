package com.example.demojpa.blog.services;

import com.example.demojpa.blog.api.dto.PostDto;
import com.example.demojpa.blog.api.mappers.PostMapper;
import com.example.demojpa.blog.models.Post;
import com.example.demojpa.blog.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    public Long postArticle(PostDto createPostDto) {
        Post post = postMapper.toPost(createPostDto);
//        Post post = Post.builder()
//                .title(createPostDto.getTitle())
//                .content(createPostDto.getContent())
//                .author(createPostDto.getAuthor())
//                .build();
        postRepository.save(post);

        return post.getId();
    }
}
