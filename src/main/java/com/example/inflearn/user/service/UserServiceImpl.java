package com.example.inflearn.user.service;

import com.example.inflearn.common.domain.exception.ResourceNotFoundException;
import com.example.inflearn.common.service.port.ClockHolder;
import com.example.inflearn.common.service.port.UuidHolder;
import com.example.inflearn.user.controller.port.UserService;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserCreate;
import com.example.inflearn.user.domain.UserStatus;
import com.example.inflearn.user.domain.UserUpdate;
import com.example.inflearn.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CertificationService certificationService;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    @Transactional
    public User create(UserCreate userCreate) {
        User user = User.from(userCreate, uuidHolder);
        user = userRepository.save(user);
        certificationService.send(user.getEmail(), user.getId(), user.getCertificationCode());
        return user;
    }

    @Override
    @Transactional
    public User update(Long id, UserUpdate userUpdate) {
        User user = getById(id);
        user = user.update(userUpdate);
        user = userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public void login(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
        user = user.login(clockHolder);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void verifyEmail(Long id, String certificationCode) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
        user = user.certificate(certificationCode);
        userRepository.save(user);
    }


}
