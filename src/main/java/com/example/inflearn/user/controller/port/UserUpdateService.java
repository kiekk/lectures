package com.example.inflearn.user.controller.port;

import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserCreate;
import com.example.inflearn.user.domain.UserUpdate;
import org.springframework.transaction.annotation.Transactional;

public interface UserUpdateService {
    User update(Long id, UserUpdate userUpdate);

    void login(Long id);

    void verifyEmail(Long id, String certificationCode);
}
