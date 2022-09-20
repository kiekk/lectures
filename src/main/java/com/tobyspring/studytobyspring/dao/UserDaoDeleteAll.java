package com.tobyspring.studytobyspring.dao;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
템플릿 메소드 패턴으로 해결?
- 상속을 통해 기능을 확장해서 사용하기 때문에 공통 기능을 슈퍼 클래스에 두고
변하는 부분은 추상 메소드로 정의해서 서브 클래스에서 구현해서 사용하도록 하는 방법입니다.
따라서 슈퍼 클래스는 추상 클래스로 선언되어야 하는데, 지금 이 문제를 템플릿 메소드 패턴으로 해결한다면
각 기능마다 슈퍼 클래스를 상속 받은 서브 클래스를 만들어야 합니다.

그리고 이미 확장 구조가 클래스를 설계하는 시점에 고정되어 버리기 때문에 유연성이 떨어져 버립니다.
 */
@Component
public class UserDaoDeleteAll extends UserDao {
    @Override
    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        return c.prepareStatement("delete from users");
    }
}
