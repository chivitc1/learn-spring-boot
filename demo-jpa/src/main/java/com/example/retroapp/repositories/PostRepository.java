package com.example.retroapp.repositories;

import com.example.retroapp.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
