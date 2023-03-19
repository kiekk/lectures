package com.example.tobyspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StudySpringBootInjectInitializerCodeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(StudySpringBootInjectInitializerCodeApplication.class, args);
        // Listener 등록
        ac.addApplicationListener(event -> System.out.println("Hello ApplicationEvent: " + event));

        // Event Publish
        ac.publishEvent(new ApplicationEvent(ac) {
        });
    }

}