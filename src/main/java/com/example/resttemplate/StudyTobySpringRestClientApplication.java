package com.example.resttemplate;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StudyTobySpringRestClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyTobySpringRestClientApplication.class, args);
    }

    @Bean
    ApplicationRunner init() {
        return args -> {
            // https://open.er-api.com/v6/latest
            RestTemplate restTemplate = new RestTemplate();
            String res = restTemplate.getForObject("https://open.er-api.com/v6/latest", String.class);
            System.out.println(res);
        };
    }
}
