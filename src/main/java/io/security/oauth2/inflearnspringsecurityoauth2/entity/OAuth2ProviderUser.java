package io.security.oauth2.inflearnspringsecurityoauth2.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

public abstract class OAuth2ProviderUser implements ProviderUser {
    private final Map<String, Object> attributes;
    OAuth2User oAuth2User;
    ClientRegistration clientRegistration;

    public OAuth2ProviderUser(Map<String, Object> attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        this.attributes = attributes;
        this.oAuth2User = oAuth2User;
        this.clientRegistration = clientRegistration;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getProvider() {
        return null;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
