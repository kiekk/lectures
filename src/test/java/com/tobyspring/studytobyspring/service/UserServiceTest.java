package com.tobyspring.studytobyspring.service;

import com.tobyspring.studytobyspring.dao.UserDao;
import com.tobyspring.studytobyspring.domain.User;
import com.tobyspring.studytobyspring.enums.Level;
import com.tobyspring.studytobyspring.policy.UserLevelUpgradePolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    UserLevelUpgradePolicy policy;

    @Autowired
    DataSource dataSource;

    @Autowired
    PlatformTransactionManager transactionManager;

    List<User> users;

    @Test
    public void bean() {
        assertNotNull(this.userService);
    }

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("1", "soonho", "1234", Level.BASIC, 49, 0, "dbstnsgh2@naver.com"),
                new User("2", "sooni", "1234", Level.BASIC, 50, 10, "dbstnsgh2@naver.com"),
                new User("3", "zooni", "1234", Level.SILVER, 60, 29, "dbstnsgh2@naver.com"),
                new User("4", "오민규", "1234", Level.SILVER, 60, 30, "dbstnsgh2@naver.com"),
                new User("5", "박범진", "1234", Level.GOLD, 100, 100, "dbstnsgh2@naver.com")
        );
    }

    @Test
    public void upgradeLevels() throws Exception {
        userDao.deleteAll();

        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4); // GOLD
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null); // level 이 비어 있는 사용자

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
        assertEquals(userWithoutLevelRead.getLevel(), userWithoutLevel.getLevel());
    }

    // 이제 어떤 레벨로 바뀔 것인가가 아니라
    // 다음 레벨로 업그레이드 될 것인가 아닌가를 지정한다.
    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }

}