package io.security.oauth2.inflearnspringsecurityoauth2.service;

import io.security.oauth2.inflearnspringsecurityoauth2.entity.*;
import io.security.oauth2.inflearnspringsecurityoauth2.entity.social.*;
import io.security.oauth2.inflearnspringsecurityoauth2.entity.users.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Getter
public abstract class AbstractOAuth2UserService {

    @Autowired
    private UserService userService;

    public ProviderUser providerUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        String registrationId = clientRegistration.getRegistrationId();

        switch (registrationId) {
            case "keycloak" -> {
                return new KeycloakUser(oAuth2User, clientRegistration);
            }
            case "google" -> {
                return new GoogleUser(oAuth2User, clientRegistration);
            }
            case "naver" -> {
                return new NaverUser(oAuth2User, clientRegistration);
            }
            case "kakao" -> {
                return new KakaoUser(oAuth2User, clientRegistration);
            }
            case "facebook" -> {
                return new FacebookUser(oAuth2User, clientRegistration);
            }
            default -> {
                return null;
            }
        }
    }

    public void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {
        User user = userService.findByUsername(providerUser.getUsername());
        if (user == null) {
            userService.register(userRequest.getClientRegistration().getRegistrationId(), providerUser);
        } else {
            System.out.println("user : " + user);
        }
    }
}
