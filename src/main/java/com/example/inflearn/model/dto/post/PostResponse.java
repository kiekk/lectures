package com.example.inflearn.model.dto.post;

import com.example.inflearn.model.dto.user.UserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {

    private Long id;
    private String content;
    private Long createdAt;
    private Long modifiedAt;
    private UserResponse writer;

}
