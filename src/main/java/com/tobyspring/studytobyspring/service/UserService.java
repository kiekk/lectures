package com.tobyspring.studytobyspring.service;

import com.tobyspring.studytobyspring.dao.UserDao;
import com.tobyspring.studytobyspring.domain.User;
import com.tobyspring.studytobyspring.enums.Level;
import com.tobyspring.studytobyspring.policy.UserLevelUpgradePolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    private final UserDao userDao;
    private final UserLevelUpgradePolicy policy;

    public UserService(UserDao userDao, UserLevelUpgradePolicy policy) {
        this.userDao = userDao;
        this.policy = policy;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();

        for (User user : users) {
            if (policy.canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    public void upgradeLevel(User user) {
        policy.upgradeLevel(user);
    }

    public void add(User user) {
        // level 이 비어 있을 경우 BASIC 으로 초기화
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

}
