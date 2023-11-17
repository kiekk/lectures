package com.example.inflearn.user.infrastructure;

import com.example.inflearn.user.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdAndStatus(Long id, UserStatus userStatus);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);
}
