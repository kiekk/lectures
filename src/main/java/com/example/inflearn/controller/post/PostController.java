package com.example.inflearn.controller.post;

import com.example.inflearn.controller.user.UserController;
import com.example.inflearn.model.dto.post.PostResponse;
import com.example.inflearn.model.dto.post.PostUpdateDto;
import com.example.inflearn.repository.post.PostEntity;
import com.example.inflearn.service.post.PostService;
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
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        return toResponse(postService.updatePost(id, postUpdateDto));
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
