package com.tobyspring.studytobyspring;

import com.tobyspring.studytobyspring.dao.UserDao;
import com.tobyspring.studytobyspring.domain.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class StudyTobySpringApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        SpringApplication.run(StudyTobySpringApplication.class, args);
        UserDao dao = new UserDao();
        User user = new User();

        user.setId("soono");
        user.setName("순호");
        user.setPassword("1234");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");

        /*
        현재 UserDao 문제점:
        첫 실행은 문제 없지만 두 번째 실행은 에러 발생
        Duplicate entry 'soono' for key 'users.PRIMARY'
        key를 직접 입력 받는 것이 아니라 자동 증가로 처리되도록 해야함
         */
    }

}
