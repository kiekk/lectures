package com.example.inflearn.post.service;

import com.example.inflearn.common.domain.exception.ResourceNotFoundException;
import com.example.inflearn.common.service.port.ClockHolder;
import com.example.inflearn.post.domain.Post;
import com.example.inflearn.post.domain.PostCreate;
import com.example.inflearn.post.domain.PostUpdate;
import com.example.inflearn.post.service.port.PostRepository;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final ClockHolder clockHolder;

    public Post getById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreate postCreate) {
        User user = userService.getById(postCreate.getWriterId());
        Post post = Post.from(postCreate, user);
        return postRepository.save(post);
    }

    public Post update(Long id, PostUpdate postUpdate) {
        Post post = getById(id);
        post = post.update(postUpdate, clockHolder);
        return postRepository.save(post);
    }

}
