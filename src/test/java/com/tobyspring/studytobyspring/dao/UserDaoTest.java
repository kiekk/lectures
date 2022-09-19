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

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        User user3 = new User();
        user3.setId("zooni");
        user3.setName("주니");
        user3.setPassword("1234");

        dao.add(user3);

        User user4 = dao.get(user3.getId());

        assertThat(user3.getName()).isEqualTo(user4.getName());
        assertThat(user3.getPassword()).isEqualTo(user4.getPassword());
    }
}