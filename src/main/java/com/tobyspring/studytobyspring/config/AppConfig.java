package com.tobyspring.studytobyspring.config;

import com.tobyspring.studytobyspring.service.UserService;
import com.tobyspring.studytobyspring.service.impl.UserServiceTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AppConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public UserService userServiceTx() {
        UserServiceTx userService = new UserServiceTx();
        userService.setUserService(this.userService);
        userService.setTransactionManager(transactionManager);
        return userService;
    }
}
