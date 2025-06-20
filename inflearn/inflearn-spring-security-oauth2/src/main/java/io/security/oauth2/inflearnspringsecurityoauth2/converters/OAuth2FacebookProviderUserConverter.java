package io.security.oauth2.inflearnspringsecurityoauth2.converters;

import io.security.oauth2.inflearnspringsecurityoauth2.entity.ProviderUser;
import io.security.oauth2.inflearnspringsecurityoauth2.entity.social.FacebookUser;
import io.security.oauth2.inflearnspringsecurityoauth2.enums.OAuth2Config;
import io.security.oauth2.inflearnspringsecurityoauth2.util.OAuth2Utils;

import java.util.Objects;

public class OAuth2FacebookProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        if (!Objects.equals(providerUserRequest.clientRegistration().getRegistrationId(), OAuth2Config.SocialType.FACEBOOK.getSocialName())) {
            return null;
        }
        return new FacebookUser(OAuth2Utils.getMainAttributes(providerUserRequest.oAuth2User()),
                providerUserRequest.oAuth2User(),
                providerUserRequest.clientRegistration());
    }
}
