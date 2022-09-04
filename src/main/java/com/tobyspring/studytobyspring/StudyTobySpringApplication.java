package com.tobyspring.studytobyspring;

import com.tobyspring.studytobyspring.dao.DaoFactory;
import com.tobyspring.studytobyspring.dao.UserDao;
import com.tobyspring.studytobyspring.domain.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.UUID;

@SpringBootApplication
public class StudyTobySpringApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        SpringApplication.run(StudyTobySpringApplication.class, args);

        // DaoFactory Bean 으로 생성
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        System.out.println("");
        UserDao dao = context.getBean("userDao", UserDao.class);
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
