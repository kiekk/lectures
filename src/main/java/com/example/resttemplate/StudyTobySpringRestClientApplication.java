package com.example.resttemplate;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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

            WebClient client = WebClient.create("https://open.er-api.com");
            Map<String, Map<String, Double>> res2  = client.get().uri("/v6/latest").retrieve().bodyToMono(Map.class).block();
            System.out.println(res2.get("rates").get("USD"));
        };
    }
}
