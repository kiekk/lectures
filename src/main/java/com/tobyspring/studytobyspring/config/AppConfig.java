package com.tobyspring.studytobyspring.config;

import com.tobyspring.studytobyspring.proxy.TxProxyFactoryBean;
import com.tobyspring.studytobyspring.service.UserService;
import com.tobyspring.studytobyspring.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AppConfig {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TxProxyFactoryBean userService() {
        TxProxyFactoryBean factoryBean = new TxProxyFactoryBean();
        factoryBean.setTarget(this.userServiceImpl);
        factoryBean.setTransactionManager(this.transactionManager);
        factoryBean.setPattern("upgradeLevels");
        factoryBean.setServiceInterface(UserService.class);
        return factoryBean;
    }
}
