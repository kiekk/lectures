package com.tobyspring.studytobyspring;

import com.tobyspring.studytobyspring.dao.UserDao;
import com.tobyspring.studytobyspring.domain.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

@SpringBootApplication
public class StudyTobySpringApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        SpringApplication.run(StudyTobySpringApplication.class, args);

        // 의존관계 검색을 이용하는 UserDao
        UserDao dao = new UserDao();
        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setName("순호");
        user.setPassword("1234");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());

        if (!Objects.equals(user.getName(), user2.getName())) {
            System.out.println("테스트 실패 (name)");
        } else if (!Objects.equals(user.getPassword(), user2.getPassword())) {
            System.out.println("테스트 실패 (password)");
        } else {
            System.out.println("조회 테스트 성공");
        }

    }

}
