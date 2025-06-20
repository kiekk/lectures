package com.example.inflearn.user.controller.port;

import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserCreate;
import com.example.inflearn.user.domain.UserUpdate;

public interface UserService {
    User getByEmail(String email);

    User getById(Long id);

    User create(UserCreate userCreate);

    User update(Long id, UserUpdate userUpdate);

    void login(Long id);

    void verifyEmail(Long id, String certificationCode);
}
