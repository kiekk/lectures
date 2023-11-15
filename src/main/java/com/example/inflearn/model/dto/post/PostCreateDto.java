package com.example.inflearn.model.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateDto {

    private final Long writerId;
    private final String content;

    @Builder
    public PostCreateDto(Long writerId, String content) {
        this.writerId = writerId;
        this.content = content;
    }

}
