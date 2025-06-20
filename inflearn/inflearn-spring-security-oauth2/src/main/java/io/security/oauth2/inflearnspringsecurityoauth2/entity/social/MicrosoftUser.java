package io.security.oauth2.inflearnspringsecurityoauth2.entity.social;

import io.security.oauth2.inflearnspringsecurityoauth2.entity.Attributes;
import io.security.oauth2.inflearnspringsecurityoauth2.entity.OAuth2ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class MicrosoftUser extends OAuth2ProviderUser {

    public MicrosoftUser(Attributes mainAttributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(mainAttributes.getMainAttributes(), oAuth2User, clientRegistration);
    }

    @Override
    public String getId() {
        return (String) getAttributes().get("unique_name");
    }

    @Override
    public String getUsername() {
        return (String) getAttributes().get("unique_name");
    }

    @Override
    public String getPicture() {
        return null;
    }
}
