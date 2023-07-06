package com.example.actuatordemo.config;

import com.example.actuatordemo.custom.MyLibraryInfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomEndpointConfig {

    @Bean
    public MyLibraryInfoEndpoint myLibraryInfoEndpoint() {
        return new MyLibraryInfoEndpoint();
    }
}
