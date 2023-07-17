package io.security.oauth2.inflearnspringsecurityoauth2.converters;

import io.security.oauth2.inflearnspringsecurityoauth2.entity.ProviderUser;
import io.security.oauth2.inflearnspringsecurityoauth2.entity.social.KeycloakUser;
import io.security.oauth2.inflearnspringsecurityoauth2.enums.OAuth2Config;

import java.util.Objects;

public class OAuth2KeycloakProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        if (!Objects.equals(providerUserRequest.clientRegistration().getRegistrationId(), OAuth2Config.SocialType.KEYCLOAK.getSocialName())) {
            return null;
        }
        return new KeycloakUser(providerUserRequest.oAuth2User(),
                providerUserRequest.clientRegistration());
    }
}
