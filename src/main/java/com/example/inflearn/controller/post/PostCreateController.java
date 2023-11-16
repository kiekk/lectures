package com.example.inflearn.controller.post;

import com.example.inflearn.model.dto.post.PostCreateDto;
import com.example.inflearn.model.dto.post.PostResponse;
import com.example.inflearn.service.post.PostService;
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
    public PostResponse createPost(@RequestBody PostCreateDto postCreateDto) {
        return postController.toResponse(postService.create(postCreateDto));
    }

}
