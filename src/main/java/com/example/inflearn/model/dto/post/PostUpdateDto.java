package com.example.inflearn.model.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdateDto {

    private final String content;

    @Builder
    public PostUpdateDto(String content) {
        this.content = content;
    }

}
