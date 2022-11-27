package com.tobyspring.studytobyspring.domain;

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

    private static final int BASIC = 1;
    private static final int SILVER = 2;
    private static final int GOLD = 3;

    private int level;
    /*

    사용자 레벨 관리 기능 추가로 level 필드를 추가했다.
    level 필드는 단순히 int 형이기 때문에 의도하지 않은 다른 값을 넣어도 에러가 발생하지 않는다.

    설계 의도는 BASIC, SILVER, GOLD 세 레벨만 관리하려고 했는데, 1000을 넣어도 에러가 발생하지 않는다.
    user.setLevel(1000);

     */

}
