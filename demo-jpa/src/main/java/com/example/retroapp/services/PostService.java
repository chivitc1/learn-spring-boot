package com.example.retroapp.services;

import com.example.retroapp.blog.api.dto.PostDto;
import com.example.retroapp.blog.api.mappers.PostMapper;
import com.example.retroapp.blog.models.Post;
import com.example.retroapp.repositories.PostRepository;
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
