package com.example.inflearn.user.service.port;

import com.example.inflearn.user.domain.UserStatus;
import com.example.inflearn.user.infrastructure.UserEntity;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByIdAndStatus(Long id, UserStatus userStatus);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(Long id);
}
