package com.example.inflearn.repository.user;

import com.example.inflearn.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdAndStatus(Long id, UserStatus userStatus);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);
}
