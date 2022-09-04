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

        // 직접 DaoFactory 를 생성할 경우 매번 새로운 Object 가 생성됨
        // Bean 으로 조회 시 기본 Singleton 으로 생성되기 떄문에
        // 매번 같은 Object 가 반환됨.
        DaoFactory factory = new DaoFactory();
        UserDao dao1 = factory.userDao();
        UserDao dao2 = factory.userDao();

        System.out.println("dao1 : " + dao1);
        System.out.println("dao2 : " + dao2);

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao3 = context.getBean("userDao", UserDao.class);
        UserDao dao4 = context.getBean("userDao", UserDao.class);

        System.out.println("dao3 : " + dao3);
        System.out.println("dao4 : " + dao4);

//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//        UserDao dao = context.getBean("userDao", UserDao.class);
//        User user = new User();
//
//        user.setId(UUID.randomUUID().toString());
//        user.setName("순호");
//        user.setPassword("1234");
//
//        dao.add(user);
//
//        System.out.println(user.getId() + " 등록 성공");
//
//        User user2 = dao.get(user.getId());
//        System.out.println(user2.getName());
//        System.out.println(user2.getPassword());
//        System.out.println(user2.getId() + " 조회 성공");


    }

}
