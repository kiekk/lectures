package com.example.resttemplate;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

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
            System.out.println(res.get("rates").get("KRW"));

            WebClient client = WebClient.create("https://open.er-api.com");
            Map<String, Map<String, Double>> res2 = client.get().uri("/v6/latest").retrieve().bodyToMono(Map.class).block();
            System.out.println(res2.get("rates").get("KRW"));

            HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                    .builder(WebClientAdapter.forClient(client))
                    .build();

            ErApi erApi = httpServiceProxyFactory.createClient(ErApi.class);
            Map<String, Map<String, Double>> res3 = erApi.getLatest();
            System.out.println(res3.get("rates").get("KRW"));
        };
    }

    interface ErApi {
        @GetExchange("/v6/latest")
        Map getLatest();
    }
}
