package com.tobyspring.studytobyspring;

import com.tobyspring.studytobyspring.dao.CConnectionMaker;
import com.tobyspring.studytobyspring.dao.DConnectionMaker;
import com.tobyspring.studytobyspring.dao.UserDao;
import com.tobyspring.studytobyspring.domain.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.UUID;

@SpringBootApplication
public class StudyTobySpringApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        SpringApplication.run(StudyTobySpringApplication.class, args);

        // 사용하는 쪽(client)에서 구현체 정보를 전달하여 관계설정 책임 분리
//        UserDao dao = new UserDao(new CConnectionMaker());
        UserDao dao = new UserDao(new DConnectionMaker());
        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setName("순호");
        user.setPassword("1234");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");


    }

}
