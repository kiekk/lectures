package com.example.inflearn.post.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdate {

    private String content;

    @Builder
    public PostUpdate(String content) {
        this.content = content;
    }

}
