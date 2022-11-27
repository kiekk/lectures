package com.tobyspring.studytobyspring.service;

import com.tobyspring.studytobyspring.domain.User;

public interface UserService {
    void add(User user);

    void upgradeLevels();
}
