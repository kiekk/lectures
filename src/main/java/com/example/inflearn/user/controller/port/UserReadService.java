package com.example.inflearn.user.controller.port;

import com.example.inflearn.user.domain.User;

public interface UserReadService {
    User getByEmail(String email);

    User getById(Long id);
}
