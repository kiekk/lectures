package com.example.tobyspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class StudySpringBootInjectInitializerCodeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(StudySpringBootInjectInitializerCodeApplication.class, args);
        // Event Publish
        ac.publishEvent(new MyEvent(ac, "TobySpringBoot Event"));
    }

    // Event Listener
    @EventListener(MyEvent.class)
    public void onMyEvent(MyEvent event) {
        System.out.println("Hello My Event: " + event.getMessage());
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