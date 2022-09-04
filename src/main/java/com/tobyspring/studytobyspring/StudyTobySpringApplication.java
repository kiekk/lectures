package com.tobyspring.studytobyspring;

import com.tobyspring.studytobyspring.dao.SimpleConnectionMaker;
import com.tobyspring.studytobyspring.dao.UserDao;
import com.tobyspring.studytobyspring.domain.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.UUID;

@SpringBootApplication
public class StudyTobySpringApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        SpringApplication.run(StudyTobySpringApplication.class, args);

        UserDao dao = new UserDao(new SimpleConnectionMaker());
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

        /*
        상속(is-a)이 아닌 포함(has-a) 관계 사용
        UserDao 가 SimpleConnectionMaker 정보를 알고 있어 문제가 됨.
        가령 D 사는 openConnection 메소드로 사용했다면 UserDao 의 add, get 메소드를 전부 수정해야 함
         */
   }

}
