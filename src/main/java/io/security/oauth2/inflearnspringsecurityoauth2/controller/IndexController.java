package io.security.oauth2.inflearnspringsecurityoauth2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private final ClientRegistrationRepository clientRegistrationRepository;

    public IndexController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("user")
    public OAuth2User user(Authentication authentication) {
        OAuth2AuthenticationToken authentication1 = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken authentication2 = (OAuth2AuthenticationToken) authentication;

        System.out.println("authentication1.getPrincipal() : " + authentication1.getPrincipal());
        System.out.println("authentication2.getPrincipal() : " + authentication2.getPrincipal());

        return authentication2.getPrincipal();
    }

    @GetMapping("oauth2User")
    public OAuth2User oauth2User(@AuthenticationPrincipal OAuth2User oAuth2User) {
        System.out.println("oAuth2User : " + oAuth2User);
        return oAuth2User;
    }

    @GetMapping("oidcUser")
    public OidcUser oidcUser(@AuthenticationPrincipal OidcUser oidcUser) {
        System.out.println("oidcUser : " + oidcUser);
        return oidcUser;
    }
}
