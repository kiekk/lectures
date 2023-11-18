package com.example.inflearn.user.service.port;

import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserStatus;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByIdAndStatus(Long id, UserStatus userStatus);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    User save(User user);

    Optional<User> findById(Long id);
}
