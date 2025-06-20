package io.security.oauth2.inflearnspringsecurityoauth2.service;

import io.security.oauth2.inflearnspringsecurityoauth2.entity.ProviderUser;
import io.security.oauth2.inflearnspringsecurityoauth2.entity.users.User;
import io.security.oauth2.inflearnspringsecurityoauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(String registrationId, ProviderUser providerUser) {
        User user = User.builder()
                .registrationId(registrationId)
                .username(providerUser.getUsername())
                .id(providerUser.getId())
                .password(providerUser.getPassword())
                .email(providerUser.getEmail())
                .provider(providerUser.getProvider())
                .authorities(providerUser.getAuthorities())
                .build();
        userRepository.register(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
