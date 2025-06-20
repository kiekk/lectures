# ☘️ 기억하기 인증 – rememberMe()

---

## 📖 내용
- RememberMe는 사용자가 웹 사이트/애플리케이션에 로그인할 때 자동으로 인증 정보를 기억하는 기능입니다.
- `UsernamePasswordAuthenticationFilter`와 함께 사용되며, `AbstractAuthenticationProcessingFilter` 클래스의 훅을 통해 구현됩니다.
  - 인증 성공 시 `RememberMeServices.loginSuccess()` 를 통해 RememberMe 토큰을 생성하고 쿠키로 전달합니다.
  - 인증 실패 시 `RememberMeServices.loginFail()` 을 통해 RememberMe 쿠키를 삭제합니다.
  - `LogoutFilter` 와 연계해서 로그아웃 시 RememberMe 쿠키를 삭제합니다.

- RememberMeServices 구현체
  - `TokenBasedRememberMeServices` : 쿠키 기반 토큰의 보안을 위해 해싱을 사용합니다.
  - `PersistentTokenBasedRememberMeServices` : DB나 다른 영구 저장소에 토큰을 저장합니다.
    - 두 구현체 모두 사용자의 정보를 검색하기 위한 `UserDetailsService`가 필요합니다.
```java
package org.springframework.security.config.annotation.web.configurers;

...

public final class RememberMeConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<RememberMeConfigurer<H>, H> {
    ...

   // tokenRepository (영구 저장소) 설정이 있을 경우 PersistentTokenBasedRememberMeServices 사용
  // 없을 경우 TokenBasedRememberMeServices 사용
    private AbstractRememberMeServices createRememberMeServices(H http, String key) {
        return this.tokenRepository != null ? this.createPersistentRememberMeServices(http, key) : this.createTokenBasedRememberMeServices(http, key);
    }
    
    ...
}
```

- `rememberMe()`
  - Spring Security에서는 `HttpSecurity.rememberMe()`를 통해 설정할 수 있으며 이 때 `RememberMeConfigurer` 객체가 초기화 작업을 진행합니다.

---

## 🔍 중심 로직

```java
package org.springframework.security.config.annotation.web.configurers;

...

public final class RememberMeConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<RememberMeConfigurer<H>, H> {
    private static final String DEFAULT_REMEMBER_ME_NAME = "remember-me";
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private String key;
    private RememberMeServices rememberMeServices;
    private LogoutHandler logoutHandler;
    private String rememberMeParameter = "remember-me";
    private String rememberMeCookieName = "remember-me";
    private String rememberMeCookieDomain;
    private PersistentTokenRepository tokenRepository;
    private UserDetailsService userDetailsService;
    private Integer tokenValiditySeconds;
    private Boolean useSecureCookie;
    private Boolean alwaysRemember;

    public RememberMeConfigurer() {
    }

    public RememberMeConfigurer<H> tokenValiditySeconds(int tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
        return this;
    }

    public RememberMeConfigurer<H> useSecureCookie(boolean useSecureCookie) {
        this.useSecureCookie = useSecureCookie;
        return this;
    }

    public RememberMeConfigurer<H> userDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        return this;
    }

    public RememberMeConfigurer<H> tokenRepository(PersistentTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
        return this;
    }

    public RememberMeConfigurer<H> key(String key) {
        this.key = key;
        return this;
    }

    public RememberMeConfigurer<H> rememberMeParameter(String rememberMeParameter) {
        this.rememberMeParameter = rememberMeParameter;
        return this;
    }

    public RememberMeConfigurer<H> rememberMeCookieName(String rememberMeCookieName) {
        this.rememberMeCookieName = rememberMeCookieName;
        return this;
    }

    public RememberMeConfigurer<H> rememberMeCookieDomain(String rememberMeCookieDomain) {
        this.rememberMeCookieDomain = rememberMeCookieDomain;
        return this;
    }

    public RememberMeConfigurer<H> authenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        return this;
    }

    public RememberMeConfigurer<H> rememberMeServices(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
        return this;
    }

    public RememberMeConfigurer<H> alwaysRemember(boolean alwaysRemember) {
        this.alwaysRemember = alwaysRemember;
        return this;
    }

    public void init(H http) throws Exception {
        this.validateInput();
        String key = this.getKey();
        RememberMeServices rememberMeServices = this.getRememberMeServices(http, key);
        http.setSharedObject(RememberMeServices.class, rememberMeServices);
        LogoutConfigurer<H> logoutConfigurer = (LogoutConfigurer)http.getConfigurer(LogoutConfigurer.class);
        if (logoutConfigurer != null && this.logoutHandler != null) {
            logoutConfigurer.addLogoutHandler(this.logoutHandler);
        }

        RememberMeAuthenticationProvider authenticationProvider = new RememberMeAuthenticationProvider(key);
        authenticationProvider = (RememberMeAuthenticationProvider)this.postProcess(authenticationProvider);
        http.authenticationProvider(authenticationProvider);
        this.initDefaultLoginFilter(http);
    }

    public void configure(H http) {
        RememberMeAuthenticationFilter rememberMeFilter = new RememberMeAuthenticationFilter((AuthenticationManager)http.getSharedObject(AuthenticationManager.class), this.rememberMeServices);
        if (this.authenticationSuccessHandler != null) {
            rememberMeFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
        }

        SecurityContextConfigurer<?> securityContextConfigurer = (SecurityContextConfigurer)http.getConfigurer(SecurityContextConfigurer.class);
        if (securityContextConfigurer != null && securityContextConfigurer.isRequireExplicitSave()) {
            SecurityContextRepository securityContextRepository = securityContextConfigurer.getSecurityContextRepository();
            rememberMeFilter.setSecurityContextRepository(securityContextRepository);
        }

        rememberMeFilter.setSecurityContextHolderStrategy(this.getSecurityContextHolderStrategy());
        SessionAuthenticationStrategy sessionAuthenticationStrategy = (SessionAuthenticationStrategy)http.getSharedObject(SessionAuthenticationStrategy.class);
        if (sessionAuthenticationStrategy != null) {
            rememberMeFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }

        rememberMeFilter = (RememberMeAuthenticationFilter)this.postProcess(rememberMeFilter);
        http.addFilter(rememberMeFilter);
    }
    
  ...

    private RememberMeServices getRememberMeServices(H http, String key) throws Exception {
        if (this.rememberMeServices != null) {
            if (this.rememberMeServices instanceof LogoutHandler && this.logoutHandler == null) {
                this.logoutHandler = (LogoutHandler)this.rememberMeServices;
            }

            return this.rememberMeServices;
        } else {
            AbstractRememberMeServices tokenRememberMeServices = this.createRememberMeServices(http, key);
            tokenRememberMeServices.setParameter(this.rememberMeParameter);
            tokenRememberMeServices.setCookieName(this.rememberMeCookieName);
            if (this.rememberMeCookieDomain != null) {
                tokenRememberMeServices.setCookieDomain(this.rememberMeCookieDomain);
            }

            if (this.tokenValiditySeconds != null) {
                tokenRememberMeServices.setTokenValiditySeconds(this.tokenValiditySeconds);
            }

            if (this.useSecureCookie != null) {
                tokenRememberMeServices.setUseSecureCookie(this.useSecureCookie);
            }

            if (this.alwaysRemember != null) {
                tokenRememberMeServices.setAlwaysRemember(this.alwaysRemember);
            }

            tokenRememberMeServices.afterPropertiesSet();
            this.logoutHandler = tokenRememberMeServices;
            this.rememberMeServices = tokenRememberMeServices;
            return tokenRememberMeServices;
        }
    }

    // RememberMeServices 객체 생성
    private AbstractRememberMeServices createRememberMeServices(H http, String key) {
        return this.tokenRepository != null ? this.createPersistentRememberMeServices(http, key) : this.createTokenBasedRememberMeServices(http, key);
    }

    // TokenBasedRememberMeServices 객체 생성
    private AbstractRememberMeServices createTokenBasedRememberMeServices(H http, String key) {
        UserDetailsService userDetailsService = this.getUserDetailsService(http);
        return new TokenBasedRememberMeServices(key, userDetailsService);
    }

    // PersistentTokenBasedRememberMeServices 객체 생성
    private AbstractRememberMeServices createPersistentRememberMeServices(H http, String key) {
        UserDetailsService userDetailsService = this.getUserDetailsService(http);
        return new PersistentTokenBasedRememberMeServices(key, userDetailsService, this.tokenRepository);
    }

  ...
}
```

```java
package org.springframework.security.web.authentication;

...

public interface RememberMeServices {
    Authentication autoLogin(HttpServletRequest request, HttpServletResponse response);

    void loginFail(HttpServletRequest request, HttpServletResponse response);

    void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication);
}
```

```java
package org.springframework.security.web.authentication.rememberme;

...

public class TokenBasedRememberMeServices extends AbstractRememberMeServices {
    private static final RememberMeTokenAlgorithm DEFAULT_MATCHING_ALGORITHM;
    private static final RememberMeTokenAlgorithm DEFAULT_ENCODING_ALGORITHM;
    private final RememberMeTokenAlgorithm encodingAlgorithm;
    private RememberMeTokenAlgorithm matchingAlgorithm;

    public TokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        this(key, userDetailsService, DEFAULT_ENCODING_ALGORITHM);
    }

    public TokenBasedRememberMeServices(String key, UserDetailsService userDetailsService, RememberMeTokenAlgorithm encodingAlgorithm) {
        super(key, userDetailsService);
        this.matchingAlgorithm = DEFAULT_MATCHING_ALGORITHM;
        Assert.notNull(encodingAlgorithm, "encodingAlgorithm cannot be null");
        this.encodingAlgorithm = encodingAlgorithm;
    }

    ...

    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        String username = this.retrieveUserName(successfulAuthentication);
        String password = this.retrievePassword(successfulAuthentication);
        if (!StringUtils.hasLength(username)) {
            this.logger.debug("Unable to retrieve username");
        } else {
            if (!StringUtils.hasLength(password)) {
                UserDetails user = this.getUserDetailsService().loadUserByUsername(username);
                password = user.getPassword();
                if (!StringUtils.hasLength(password)) {
                    this.logger.debug("Unable to obtain password for user: " + username);
                    return;
                }
            }

            int tokenLifetime = this.calculateLoginLifetime(request, successfulAuthentication);
            long expiryTime = System.currentTimeMillis();
            expiryTime += 1000L * (long)(tokenLifetime < 0 ? 1209600 : tokenLifetime);
            String signatureValue = this.makeTokenSignature(expiryTime, username, password, this.encodingAlgorithm);
            this.setCookie(new String[]{username, Long.toString(expiryTime), this.encodingAlgorithm.name(), signatureValue}, tokenLifetime, request, response);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Added remember-me cookie for user '" + username + "', expiry: '" + String.valueOf(new Date(expiryTime)) + "'");
            }

        }
    }

    public void setMatchingAlgorithm(RememberMeTokenAlgorithm matchingAlgorithm) {
        Assert.notNull(matchingAlgorithm, "matchingAlgorithm cannot be null");
        this.matchingAlgorithm = matchingAlgorithm;
    }

  ...

    static {
        DEFAULT_MATCHING_ALGORITHM = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
        DEFAULT_ENCODING_ALGORITHM = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
    }

    public static enum RememberMeTokenAlgorithm {
        MD5("MD5"),
        SHA256("SHA-256");

        private final String digestAlgorithm;

        private RememberMeTokenAlgorithm(String digestAlgorithm) {
            this.digestAlgorithm = digestAlgorithm;
        }

        public String getDigestAlgorithm() {
            return this.digestAlgorithm;
        }
    }
}
```

```java
import java.beans.Customizer;

@Configuration
public class SecurityConfig {

  // 1. 커스텀 설정
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .rememberMe(rememberMe ->
                    rememberMe
                            .alwaysRemember()
                            .authenticationSuccessHandler()
                            .rememberMeCookieDomain()
                            .rememberMeCookieName()
                            .rememberMeParameter()
                            .rememberMeServices()
                            .tokenRepository()
                            .tokenValiditySeconds()
                            .userDetailsService()
                            .useSecureCookie()
                            .key()
            )
            .build();
  }

  // 2. 기본 설정
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .rememberMe(Customizer.withDefaults())
            .build();
  }

}
```

📌  요약

- `rembmerMe()`
  - `alwaysRemember()` : 항상 RememberMe 기능을 사용합니다. (remember-me 파라미터가 설정되지 않았을 때도 쿠키가 항상 생성되어야 하는지)
  - `authenticationSuccessHandler()` : 인증 성공 시 처리할 핸들러를 설정합니다.
  - `rememberMeCookieDomain()` : RememberMe 쿠키의 도메인을 설정합니다.
  - `rememberMeCookieName()` : RememberMe 쿠키의 이름을 설정합니다.
  - `rememberMeParameter()` : RememberMe 파라미터의 이름을 설정합니다.
  - `rememberMeServices()` : RememberMe 서비스를 설정합니다.
  - `tokenRepository()` : RememberMe 토큰 저장소를 설정합니다.
  - `tokenValiditySeconds()` : RememberMe 토큰의 유효 기간(초 단위)을 설정합니다.
  - `userDetailsService()` : 사용자 세부 정보를 가져오는 서비스를 설정합니다.
  - `useSecureCookie()` : 보안 쿠키를 사용할지 여부를 설정합니다.
  - `key()` : RememberMe 인증을 위해 생성된 토큰을 식별하는 키를 생성합니다.

---