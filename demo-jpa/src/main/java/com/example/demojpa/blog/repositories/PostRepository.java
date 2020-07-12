package com.example.demojpa.blog.repositories;

import com.example.demojpa.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
