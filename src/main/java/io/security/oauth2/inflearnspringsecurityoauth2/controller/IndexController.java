package io.security.oauth2.inflearnspringsecurityoauth2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class IndexController {

    @GetMapping({"", "/"})
    public String index(Model model, Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User) {
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        if (auth2AuthenticationToken != null) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            String name;

            switch (auth2AuthenticationToken.getAuthorizedClientRegistrationId()) {
                case "naver" -> {
                    Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                    name = (String) response.get("name");
                }
                case "keycloak" -> {
                    name = (String) attributes.get("preferred_username");
                }
                case "kakao" -> {
                    name = (String) ((Map<String, Object>) ((Map<String, Object>) attributes.get("kakao_account")).get("profile")).get("nickname");
                }
                default -> {
                    name = (String) attributes.get("name");
                }
            }

            model.addAttribute("user", name);
        }

        return "index";
    }
}

