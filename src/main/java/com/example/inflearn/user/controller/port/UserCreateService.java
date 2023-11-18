package com.example.inflearn.user.controller.port;

import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserCreate;

public interface UserCreateService {
    User create(UserCreate userCreate);
}
