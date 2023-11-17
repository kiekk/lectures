package com.example.inflearn.post.controller;

import com.example.inflearn.post.domain.PostCreate;
import com.example.inflearn.post.controller.response.PostResponse;
import com.example.inflearn.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostCreateController {

    private final PostService postService;
    private final PostController postController;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@RequestBody PostCreate postCreate) {
        return postController.toResponse(postService.create(postCreate));
    }

}
