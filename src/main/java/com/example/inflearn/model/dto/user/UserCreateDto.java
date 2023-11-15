package com.example.inflearn.model.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateDto {

    private final String email;
    private final String nickname;
    private final String address;

    @Builder
    public UserCreateDto(String email, String nickname, String address) {
        this.email = email;
        this.nickname = nickname;
        this.address = address;
    }

}
