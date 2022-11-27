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
    /*
    상수로 관리하는 것 보다 Enum 으로 관리하는 것이 좋다.

    이제 의도하지 않은 다른 값을 level 에 넣을 경우 컴파일 단계에서 에러가 발생한다.
    user.setLevel(1000);
    ->
    user.setLevel(Level.BASIC);
     */

}
