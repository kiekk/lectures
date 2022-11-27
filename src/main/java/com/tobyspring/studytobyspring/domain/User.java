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

}
