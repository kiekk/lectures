package com.tobyspring.studytobyspring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void bean() {
        assertNotNull(this.userService);
    }
}