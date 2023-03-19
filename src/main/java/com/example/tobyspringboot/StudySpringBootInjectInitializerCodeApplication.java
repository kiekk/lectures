package com.example.tobyspringboot;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudySpringBootInjectInitializerCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudySpringBootInjectInitializerCodeApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            System.out.println("Hello Anonymous Lambda ApplicationRunner");
        };
    }
}