package com.inflearn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. 직접 객체를 생성하여 주입
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // 2가지 방법 모두 동일하게 동작
//        // 1
//        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        managerBuilder.authenticationProvider(new CustomAuthenticationProvider());
//        // 2
//        http.authenticationProvider(new CustomAuthenticationProvider2());
//        return http.build();
//    }

    // 2. AuthenticationProvider를 빈으로 등록하여 주입
    // 2-1. 빈을 한 개만 정의
    // AuthenticationProvider를 빈으로 정의하게 되면 authenticationManager의 AnonymousAuthenticationProvider, parent의 DaoAuthenticationProvider를 대체합니다.
    // parent의 DaoAuthenticationProvider, authenticationManager의 AnonymousAuthenticationProvider는 제거
    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    // 해당 방법으로 AuthenticationProvider를 등록하게 되면 parent가 아닌 authenticationManager에 추가됩니다.
    // parent의 DaoAuthenticationProvider, authenticationManager의 AnonymousAuthenticationProvider는 유지
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManagerBuilder builder, AuthenticationConfiguration configuration) throws Exception {
        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        managerBuilder.authenticationProvider(customAuthenticationProvider());

        ProviderManager providerManager = (ProviderManager) configuration.getAuthenticationManager();
        providerManager.getProviders().remove(0);
        builder.authenticationProvider(new DaoAuthenticationProvider());

        return http.build();
    }

}
