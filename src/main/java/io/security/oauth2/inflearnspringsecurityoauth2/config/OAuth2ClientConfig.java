package io.security.oauth2.inflearnspringsecurityoauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
public class OAuth2ClientConfig {

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests((requests) -> {
                    requests.antMatchers("/", "/oauth2Login", "/client", "/logout").permitAll();
                    requests.anyRequest().authenticated();
                })
                .oauth2Client()
                .and()
                .logout(logout -> {
                    logout.invalidateHttpSession(true);
                    logout.deleteCookies("JSESSIONID");
                    logout.clearAuthentication(true);
                })
                .build();
    }
}
