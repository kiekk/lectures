package com.example.inflearn.post.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdate {

    private final String content;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    @Builder
    public PostUpdate(String content) {
        this.content = content;
    }

}
