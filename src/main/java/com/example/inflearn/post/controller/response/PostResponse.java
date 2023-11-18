package com.example.inflearn.post.controller.response;

import com.example.inflearn.post.domain.Post;
import com.example.inflearn.user.controller.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;
    private final String content;
    private final Long createdAt;
    private final Long modifiedAt;
    private final UserResponse writer;

    @Builder
    public PostResponse(Long id, String content, Long createdAt, Long modifiedAt, UserResponse writer) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writer = writer;
    }

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .writer(UserResponse.from(post.getWriter()))
                .build();
    }

}
