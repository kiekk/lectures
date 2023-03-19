package com.example.tobyspringboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class StudySpringBootInjectInitializerCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudySpringBootInjectInitializerCodeApplication.class, args);
    }

}

// CommandLineRunner interface 를 구현한 객체를 Bean 으로 등록하면
// Spring Boot 초기화 후 코드를 실행할 수 있다.
@Component
class MyCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello CommandLineRunner");
    }
}