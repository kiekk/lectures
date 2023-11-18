package com.example.inflearn.post.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreate {

    private final Long writerId;
    private final String content;

    @Builder
    public PostCreate(Long writerId, String content) {
        this.writerId = writerId;
        this.content = content;
    }

}
