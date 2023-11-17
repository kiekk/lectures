package com.example.inflearn.post.service.port;

import com.example.inflearn.post.infrastructure.PostEntity;

import java.util.Optional;

public interface PostRepository {
    Optional<PostEntity> findById(Long id);

    PostEntity save(PostEntity postEntity);
}
