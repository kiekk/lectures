package com.example.inflearn.post.service.port;

import com.example.inflearn.post.domain.Post;
import com.example.inflearn.post.infrastructure.PostEntity;

import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(Long id);

    Post save(Post post);
}
