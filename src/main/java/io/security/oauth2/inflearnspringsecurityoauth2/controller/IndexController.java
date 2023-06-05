package io.security.oauth2.inflearnspringsecurityoauth2.controller;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    public OAuth2User user(String accessToken) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloak");
        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER, // 토큰 타입
                accessToken, // 토큰 값
                Instant.now(), // 발행일
                Instant.MAX // 만료일
        );
        OAuth2UserRequest oAuth2UserRequest = new OAuth2UserRequest(clientRegistration, oAuth2AccessToken);
        DefaultOAuth2UserService userService = new DefaultOAuth2UserService();
        return userService.loadUser(oAuth2UserRequest);
    }

    @GetMapping("oidc")
    public OAuth2User oidc(String accessToken, String idToken) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloak");
        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER, // 토큰 타입
                accessToken, // 토큰 값
                Instant.now(), // 발행일
                Instant.MAX, // 만료일
                Set.of("openid") // scope
        );
        Map<String, Object> idTokenClaims = Map.of(
                IdTokenClaimNames.ISS, "http://localhost:18080/realms/master",
                // 인가 서버에서 다시 인증 처리 시 sub validation 에서 오류 발생
                IdTokenClaimNames.SUB, "e83371fe-aacc-456d-a5e4-76de30abc3b3", // 계정 ID 입력
                "preferred_username", "user"
        );
        OidcIdToken oidcIdToken = new OidcIdToken(
                idToken, // 토큰 값
                Instant.now(), // 발행일
                Instant.MAX, // 만료일
                idTokenClaims
        );
        OidcUserRequest oidcUserRequest = new OidcUserRequest(clientRegistration, oAuth2AccessToken, oidcIdToken);
        OidcUserService userService = new OidcUserService();
        return userService.loadUser(oidcUserRequest);
    }
}
