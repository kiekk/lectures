package com.tobyspring.studytobyspring.domain;

import com.tobyspring.studytobyspring.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String name;
    private String password;

    private Level level;
    private int login; // 로그인 횟수
    private int recommend; // 추천수
    private String email;

    // User 스스로가 upgrade 가능 여부를 판단
    public void upgradeLevel() {
        Level nextLevel = this.level.nextLevel();

        if (nextLevel == null) {
            throw new IllegalArgumentException(this.level + "은 업그레이드가 불가능합니다.");
        } else {
            this.level = nextLevel;
        }
    }

}
