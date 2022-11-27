package com.tobyspring.studytobyspring.service;

import com.tobyspring.studytobyspring.dao.UserDao;
import com.tobyspring.studytobyspring.domain.User;
import com.tobyspring.studytobyspring.enums.Level;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();

        for (User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    public void upgradeLevel(User user) {
        if (user.getLevel() == Level.BASIC) {
            user.setLevel(Level.SILVER);
        } else if (user.getLevel() == Level.SILVER) {
            user.setLevel(Level.GOLD);
        }
        userDao.update(user);
    }

    public void add(User user) {
        // level 이 비어 있을 경우 BASIC 으로 초기화
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    /*
    upgradeLevels 리팩토링
    user 가 upgrade 가능한지 여부를 판단하는 if-else 로직을 method 로 분리하여 가독성 향상 및 핵심 로직 분리

    단점
    1. user 가 upgrade 가능한지 여부를 판단하는 기능을 UserService 가 해야 할까?
    upgrade 가능 여부를 판단하기 위해 필요한 정보는 level, login, recommend 인데 이는 전부 User 가 가지고 있는 필드이다.
    User 스스로 upgrade 가능 여부를 판단하는 것이 더 좋지 않을까?

    2. upgradeLevel 메소드에 여전히 level 을 분기 처리하는 if-else 로직이 존재한다.
    이는 새로운 Level 추가 및 Level 변경이 이뤄질 때마다 if-else 를 수정하거나 추가해야 하는 번거로움이 존재한다.
     */
    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();

        switch (currentLevel) {
            case BASIC:
                return user.getLogin() >= 50;
            case SILVER:
                return user.getRecommend() >= 30;
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }
}
