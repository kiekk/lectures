package io.security.oauth2.inflearnspringsecurityoauth2.entity.social;

import io.security.oauth2.inflearnspringsecurityoauth2.entity.OAuth2ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoUser extends OAuth2ProviderUser {

    public KakaoUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super((Map<String, Object>)oAuth2User.getAttributes().get("kakao_account"), oAuth2User, clientRegistration);
    }

    @Override
    public String getId() {
        return (String) getAttributes().get("email");
    }

    @Override
    public String getUsername() {
        return (String) ((Map<String, Object>)getAttributes().get("profile")).get("nickname");
    }

    @Override
    public String getPicture() {
        return null;
    }
}
