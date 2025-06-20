package com.example.inflearn.user.controller.response;

import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final UserStatus status;
    private final Long lastLoginAt;

    @Builder
    public UserResponse(Long id, String email, String nickname, UserStatus status, Long lastLoginAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.status = status;
        this.lastLoginAt = lastLoginAt;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .status(user.getStatus())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

}
