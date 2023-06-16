package io.security.oauth2.inflearnspringsecurityoauth2.repository;

import io.security.oauth2.inflearnspringsecurityoauth2.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final Map<String, Object> users = new HashMap<>();

    public User findByUsername(String username) {
        return (User) users.getOrDefault(username, null);
    }

    public void register(User user) {
        users.putIfAbsent(user.getUsername(), user);
    }
}
