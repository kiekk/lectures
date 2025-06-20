package com.example.inflearn.post.controller;

import com.example.inflearn.post.controller.port.PostService;
import com.example.inflearn.post.controller.response.PostResponse;
import com.example.inflearn.post.domain.PostCreate;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Builder
public class PostCreateController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@RequestBody PostCreate postCreate) {
        return PostResponse.from(postService.create(postCreate));
    }

}
