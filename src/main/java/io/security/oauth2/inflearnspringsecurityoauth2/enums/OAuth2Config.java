package io.security.oauth2.inflearnspringsecurityoauth2.enums;

public class OAuth2Config {

    public enum SocialType {
        KEYCLOAK("keycloak"),
        GOOGLE("google"),
        NAVER("naver"),
        KAKAO("kakao"),
        FACEBOOK("facebook"),
        APPLE("apple"),
        MICROSOFT("microsoft");

        private final String socialName;

        SocialType(String socialName) {
            this.socialName = socialName;
        }

        public String getSocialName() {
            return socialName;
        }
    }

}
