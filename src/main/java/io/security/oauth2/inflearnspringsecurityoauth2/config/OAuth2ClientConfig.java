package io.security.oauth2.inflearnspringsecurityoauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuth2ClientConfig {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests((authz) -> {
                    authz.antMatchers("/home").permitAll();
                    authz.anyRequest().authenticated();
                })
                .oauth2Login(oauth2Login -> {
                    oauth2Login.authorizationEndpoint(authEndpoint -> {
                        authEndpoint.authorizationRequestResolver(customOAuthRequestResolver());
                    });
                })
                .build();
    }

    private OAuth2AuthorizationRequestResolver customOAuthRequestResolver() {
        return new CustomOAuth2AuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization");
    }

}
