# ☘️ 익명 인증 사용자 - anonymous()

---

## 📖 내용
- `anonymous()`
  - Spring Security에서는 권한 처리를 구성하는 더 편리한 방법을 제공하기 위해 "익명 사용자"를 구부내한다고 볼 수 있습니다.
  - SecurityContextHolder가 항상 Authentication 객체를 포함하고 null을 포함하지 않는다는 것을 규칙을 세우게 되면 클래스를 더 견고하게 작성할 수 있습니다.
  - 인증 사용자와 익명 인증 사용자를 구분해서 어떤 기능을 수행하고자 할 때 유용할 수 있으며 익명 인증 객체를 세션에 저장하지 않습니다.
  - 익명 인증 사용자의 권한을 별도로 운용할 수도 있습니다.

- `anonymous()`를 메서드를 사용하여 활성화할 수 있으며 `AnonymousConfigurer`를 사용하여 익명 인증 사용자의 권한을 설정할 수 있습니다.

---

## 🔍 중심 로직

```java
package org.springframework.security.config.annotation.web.configurers;

...

public final class AnonymousConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<AnonymousConfigurer<H>, H> {
    private String key;
    private AuthenticationProvider authenticationProvider;
    private AnonymousAuthenticationFilter authenticationFilter;
    private Object principal = "anonymousUser";
    private List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(new String[]{"ROLE_ANONYMOUS"});
    private String computedKey;

    public AnonymousConfigurer() {
    }

    public AnonymousConfigurer<H> key(String key) {
        this.key = key;
        return this;
    }

    public AnonymousConfigurer<H> principal(Object principal) {
        this.principal = principal;
        return this;
    }

    public AnonymousConfigurer<H> authorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public AnonymousConfigurer<H> authorities(String... authorities) {
        return this.authorities(AuthorityUtils.createAuthorityList(authorities));
    }

    public AnonymousConfigurer<H> authenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        return this;
    }

    public AnonymousConfigurer<H> authenticationFilter(AnonymousAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
        return this;
    }

    public void init(H http) {
        if (this.authenticationProvider == null) {
            this.authenticationProvider = new AnonymousAuthenticationProvider(this.getKey());
        }

        this.authenticationProvider = (AuthenticationProvider)this.postProcess(this.authenticationProvider);
        http.authenticationProvider(this.authenticationProvider);
    }

    public void configure(H http) {
        if (this.authenticationFilter == null) {
            this.authenticationFilter = new AnonymousAuthenticationFilter(this.getKey(), this.principal, this.authorities);
        }

        this.authenticationFilter.setSecurityContextHolderStrategy(this.getSecurityContextHolderStrategy());
        this.authenticationFilter.afterPropertiesSet();
        http.addFilter(this.authenticationFilter);
    }

    private String getKey() {
        if (this.computedKey != null) {
            return this.computedKey;
        } else {
            if (this.key == null) {
                this.computedKey = UUID.randomUUID().toString();
            } else {
                this.computedKey = this.key;
            }

            return this.computedKey;
        }
    }
}
```

```java
package org.springframework.security.web.authentication;

...

public class AnonymousAuthenticationFilter extends GenericFilterBean implements InitializingBean {
    private SecurityContextHolderStrategy securityContextHolderStrategy;
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;
    private String key;
    private Object principal;
    private List<GrantedAuthority> authorities;

    public AnonymousAuthenticationFilter(String key) {
        this(key, "anonymousUser", AuthorityUtils.createAuthorityList(new String[]{"ROLE_ANONYMOUS"}));
    }

    public AnonymousAuthenticationFilter(String key, Object principal, List<GrantedAuthority> authorities) {
        this.securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        this.authenticationDetailsSource = new WebAuthenticationDetailsSource();
        Assert.hasLength(key, "key cannot be null or empty");
        Assert.notNull(principal, "Anonymous authentication principal must be set");
        Assert.notNull(authorities, "Anonymous authorities must be set");
        this.key = key;
        this.principal = principal;
        this.authorities = authorities;
    }

    public void afterPropertiesSet() {
        Assert.hasLength(this.key, "key must have length");
        Assert.notNull(this.principal, "Anonymous authentication principal must be set");
        Assert.notNull(this.authorities, "Anonymous authorities must be set");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        Supplier<SecurityContext> deferredContext = this.securityContextHolderStrategy.getDeferredContext();
        this.securityContextHolderStrategy.setDeferredContext(this.defaultWithAnonymous((HttpServletRequest)req, deferredContext));
        chain.doFilter(req, res);
    }

    private Supplier<SecurityContext> defaultWithAnonymous(HttpServletRequest request, Supplier<SecurityContext> currentDeferredContext) {
        return SingletonSupplier.of(() -> {
            SecurityContext currentContext = (SecurityContext)currentDeferredContext.get();
            return this.defaultWithAnonymous(request, currentContext);
        });
    }

    private SecurityContext defaultWithAnonymous(HttpServletRequest request, SecurityContext currentContext) {
        Authentication currentAuthentication = currentContext.getAuthentication();
        if (currentAuthentication == null) {
            Authentication anonymous = this.createAuthentication(request);
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.of(() -> "Set SecurityContextHolder to " + String.valueOf(anonymous)));
            } else {
                this.logger.debug("Set SecurityContextHolder to anonymous SecurityContext");
            }

            SecurityContext anonymousContext = this.securityContextHolderStrategy.createEmptyContext();
            anonymousContext.setAuthentication(anonymous);
            return anonymousContext;
        } else {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.of(() -> "Did not set SecurityContextHolder since already authenticated " + String.valueOf(currentAuthentication)));
            }

            return currentContext;
        }
    }

    protected Authentication createAuthentication(HttpServletRequest request) {
        AnonymousAuthenticationToken token = new AnonymousAuthenticationToken(this.key, this.principal, this.authorities);
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return token;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        Assert.notNull(securityContextHolderStrategy, "securityContextHolderStrategy cannot be null");
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public List<GrantedAuthority> getAuthorities() {
        return this.authorities;
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
                .anonymous(anonymous ->
                        anonymous
                                .principal()
                                .authorities()
                                .authenticationFilter()
                                .authenticationProvider()
                )
                .build();
    }

    // 2. 기본 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .anonymous(Customizer.withDefaults())
                .build();
    }
}
```

📌  요약

- `anonymous()`
  - `principal()`: 익명 인증 사용자의 기본 인증 객체를 설정합니다. (default: anonymousUser)
  - `authorities()`: 익명 인증 사용자의 권한을 설정합니다.
  - `authenticationFilter()`: 익명 인증 필터를 설정합니다.
  - `authenticationProvider()`: 익명 인증 제공자를 설정합니다.

```java
public class AnonymousAuthenticationFilter extends GenericFilterBean implements InitializingBean {

    ...

    private SecurityContext defaultWithAnonymous(HttpServletRequest request, SecurityContext currentContext) {
        Authentication currentAuthentication = currentContext.getAuthentication();
        if (currentAuthentication == null) {
            
            ...
            
            // 익명 인증 객체는 세션에 저장하지 않는다.
            SecurityContext anonymousContext = this.securityContextHolderStrategy.createEmptyContext();
            anonymousContext.setAuthentication(anonymous);
            return anonymousContext;
        } else {
          
            ...
        }
    }
    
    ...
}
```
- 익명 사용자는 SecurityContext에 익명 인증 사용자 객체가 저장되어도 세션에는 저장하지 않습니다.

---
