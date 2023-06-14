package io.security.oauth2.inflearnspringsecurityoauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private OAuth2AuthorizedClientService auth2AuthorizedClientService;

    @GetMapping("home")
    public String home(Model model, Authentication authentication) {
        OAuth2AuthorizedClient oAuth2AuthorizedClient = auth2AuthorizedClientService.loadAuthorizedClient("keycloak", authentication.getName());
        model.addAttribute("accessToken", oAuth2AuthorizedClient.getAccessToken().getTokenValue());
        model.addAttribute("refreshToken", oAuth2AuthorizedClient.getRefreshToken().getTokenValue());
        return "home";
    }
}
