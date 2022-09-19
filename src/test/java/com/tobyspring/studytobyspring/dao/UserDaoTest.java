package com.tobyspring.studytobyspring.dao;

import com.tobyspring.studytobyspring.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class UserDaoTest {

    @Autowired
    private UserDao dao;

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setName("soonho");
        user.setPassword("1234");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());

        assertThat(user2.getName()).isEqualTo(user.getName());
        assertThat(user2.getPassword()).isEqualTo(user.getPassword());
    }
}