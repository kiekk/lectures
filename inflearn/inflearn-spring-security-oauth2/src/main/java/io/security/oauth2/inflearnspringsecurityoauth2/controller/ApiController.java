package io.security.oauth2.inflearnspringsecurityoauth2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

    @GetMapping("user")
    public Authentication user(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User) {
        System.out.println("authentication : " + authentication + ", oAUth2User : " + oAuth2User);
        return authentication;
    }

    @GetMapping("oidc")
    public Authentication opidc(Authentication authentication, @AuthenticationPrincipal OidcUser oidcUser) {
        System.out.println("authentication : " + authentication + ", oidcUser : " + oidcUser);
        return authentication;
    }

}
