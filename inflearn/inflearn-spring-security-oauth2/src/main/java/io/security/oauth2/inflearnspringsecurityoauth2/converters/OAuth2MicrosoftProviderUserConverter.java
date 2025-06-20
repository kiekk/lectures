package io.security.oauth2.inflearnspringsecurityoauth2.converters;

import io.security.oauth2.inflearnspringsecurityoauth2.entity.ProviderUser;
import io.security.oauth2.inflearnspringsecurityoauth2.entity.social.MicrosoftUser;
import io.security.oauth2.inflearnspringsecurityoauth2.enums.OAuth2Config;
import io.security.oauth2.inflearnspringsecurityoauth2.util.OAuth2Utils;

import java.util.Objects;

public class OAuth2MicrosoftProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        if (!Objects.equals(providerUserRequest.clientRegistration().getRegistrationId(), OAuth2Config.SocialType.MICROSOFT.getSocialName())) {
            return null;
        }
        return new MicrosoftUser(OAuth2Utils.getMainAttributes(providerUserRequest.oAuth2User()),
                providerUserRequest.oAuth2User(),
                providerUserRequest.clientRegistration());
    }
}
