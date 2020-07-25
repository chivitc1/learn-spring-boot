package com.example.retroapp.blog.api.mappers;

import com.example.retroapp.blog.api.dto.PostDto;
import com.example.retroapp.blog.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post toPost(PostDto postDto);
}
