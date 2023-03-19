package com.example.tobyspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StudySpringBootInjectInitializerCodeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(StudySpringBootInjectInitializerCodeApplication.class, args);
        // Listener 등록
        ac.addApplicationListener((ApplicationListener<MyEvent>) event -> System.out.println("Hello ApplicationEvent: " + event.getMessage()));

        // Event Publish
        ac.publishEvent(new MyEvent(ac, "TobySpringBoot Event"));
    }

    static class MyEvent extends ApplicationEvent {
        private final String message;

        public MyEvent(Object source, String message) {
            super(source);
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}