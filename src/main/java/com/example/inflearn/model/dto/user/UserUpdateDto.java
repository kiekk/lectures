package com.example.inflearn.model.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateDto {

    private final String nickname;
    private final String address;

    @Builder
    public UserUpdateDto(String nickname, String address) {
        this.nickname = nickname;
        this.address = address;
    }

}
