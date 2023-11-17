package com.example.inflearn.user.infrastructure;

import com.example.inflearn.user.domain.UserStatus;
import com.example.inflearn.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<UserEntity> findByIdAndStatus(Long id, UserStatus userStatus) {
        return userJpaRepository.findByIdAndStatus(id, userStatus);
    }

    @Override
    public Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus) {
        return userJpaRepository.findByEmailAndStatus(email, userStatus);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userJpaRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userJpaRepository.findById(id);
    }

}
