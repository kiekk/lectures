package com.example.inflearn.user.controller.port;

import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserCreate;
import com.example.inflearn.user.domain.UserUpdate;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    User getByEmail(String email);

    User getById(Long id);

    @Transactional
    User create(UserCreate userCreate);

    @Transactional
    User update(Long id, UserUpdate userUpdate);

    @Transactional
    void login(Long id);

    @Transactional
    void verifyEmail(Long id, String certificationCode);
}
