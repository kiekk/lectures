package com.example.inflearn.post.controller;

import com.example.inflearn.user.controller.UserController;
import com.example.inflearn.post.controller.response.PostResponse;
import com.example.inflearn.post.domain.PostUpdate;
import com.example.inflearn.post.infrastructure.PostEntity;
import com.example.inflearn.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserController userController;

    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable Long id) {
        return toResponse(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostUpdate postUpdate) {
        return toResponse(postService.update(id, postUpdate));
    }

    public PostResponse toResponse(PostEntity postEntity) {
        PostResponse PostResponse = new PostResponse();
        PostResponse.setId(postEntity.getId());
        PostResponse.setContent(postEntity.getContent());
        PostResponse.setCreatedAt(postEntity.getCreatedAt());
        PostResponse.setModifiedAt(postEntity.getModifiedAt());
        PostResponse.setWriter(userController.toResponse(postEntity.getWriter()));
        return PostResponse;
    }

}
