package io.security.oauth2.inflearnspringsecurityoauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class AppConfig {

    @Bean
    public DefaultOAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository,
                                                                 OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .clientCredentials()
                        .password()
                        .refreshToken()
                        .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        authorizedClientManager.setContextAttributesMapper(contextAttributesMapper());

        return authorizedClientManager;
    }

    private Function<OAuth2AuthorizeRequest, Map<String, Object>> contextAttributesMapper() {
        return oAuth2AuthorizeRequest -> {
            // request 정보 가져오기
            HttpServletRequest request = oAuth2AuthorizeRequest.getAttribute(HttpServletRequest.class.getName());

            if (request != null) {
                String username = request.getParameter(OAuth2ParameterNames.USERNAME);
                String password = request.getParameter(OAuth2ParameterNames.PASSWORD);

                if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
                    return Map.of(
                            OAuth2AuthorizationContext.USERNAME_ATTRIBUTE_NAME, username,
                            OAuth2AuthorizationContext.PASSWORD_ATTRIBUTE_NAME, password
                    );
                }
            }
            return Collections.emptyMap();
        };
    }

}
