package com.inflearn.security.users.service;

import com.inflearn.security.domain.entity.Account;
import com.inflearn.security.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createUser(Account account){
        userRepository.save(account);
    }

}
