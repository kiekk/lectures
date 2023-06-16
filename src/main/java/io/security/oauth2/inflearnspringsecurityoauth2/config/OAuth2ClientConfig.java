package io.security.oauth2.inflearnspringsecurityoauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
public class OAuth2ClientConfig {

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authz) -> {
                    authz
                            // static resources setting
                            .requestMatchers("/static/**").permitAll()
                            .requestMatchers("/").permitAll()
                            .anyRequest().authenticated();
                })
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> {
                    logout.logoutSuccessUrl("/");
                })
                .build();
    }

}
