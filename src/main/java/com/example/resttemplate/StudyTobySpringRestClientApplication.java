package com.example.resttemplate;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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
            Map<String, Map<String, Double>> res = restTemplate.getForObject("https://open.er-api.com/v6/latest", Map.class);
            System.out.println(res.get("rates").get("USD"));
        };
    }
}
