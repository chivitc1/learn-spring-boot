package com.example.demojpa.blog.api.mappers;

import com.example.demojpa.blog.api.dto.PostDto;
import com.example.demojpa.blog.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post toPost(PostDto postDto);
}
