package com.example.inflearn.post.controller;

import com.example.inflearn.post.controller.port.PostService;
import com.example.inflearn.post.controller.response.PostResponse;
import com.example.inflearn.post.domain.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable Long id) {
        return PostResponse.from(postService.getById(id));
    }

    @PutMapping("/{id}")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostUpdate postUpdate) {
        return PostResponse.from(postService.update(id, postUpdate));
    }

}
