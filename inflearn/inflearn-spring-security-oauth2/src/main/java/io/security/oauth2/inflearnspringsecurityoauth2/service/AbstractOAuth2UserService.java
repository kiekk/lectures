package io.security.oauth2.inflearnspringsecurityoauth2.service;

import io.security.oauth2.inflearnspringsecurityoauth2.converters.ProviderUserConverter;
import io.security.oauth2.inflearnspringsecurityoauth2.converters.ProviderUserRequest;
import io.security.oauth2.inflearnspringsecurityoauth2.entity.ProviderUser;
import io.security.oauth2.inflearnspringsecurityoauth2.entity.users.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

@Service
@Getter
public abstract class AbstractOAuth2UserService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    public ProviderUser providerUser(ProviderUserRequest providerUserRequest) {
        return providerUserConverter.converter(providerUserRequest);
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
