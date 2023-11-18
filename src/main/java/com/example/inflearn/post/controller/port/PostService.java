package com.example.inflearn.post.controller.port;

import com.example.inflearn.post.domain.Post;
import com.example.inflearn.post.domain.PostCreate;
import com.example.inflearn.post.domain.PostUpdate;

public interface PostService {
    Post getById(Long id);

    Post create(PostCreate postCreate);

    Post update(Long id, PostUpdate postUpdate);
}
