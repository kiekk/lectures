# ☘️ 세션 생성 정책 - sessionManagement().sessionCreationPolicy()

---

## 📖 내용
- `SessionCreationPolicy`: 인증된 사용자에 대한 세션 생성 정책을 설정

- 세션 생성 정책 전략 종류
  - `SessionCreationPolicy.ALWAYS`
    - 항상 세션을 생성합니다.
    - `ForceEagerSessionCreationFilter` 클래스를 추가 구성하고 세션을 강제로 생성합니다.
  - `SessionCreationPolicy.NEVER`
    - 세션을 생성하지 않지만 애플리케이션이 이미 생성한 세션은 사용할 수 있습니다.
  - `SessionCreationPolicy.IF_REQUIRED`
    - 필요한 경우에만 세션을 생성합니다.
    - 인증이 필요한 자원에 접근할 떄 세션을 생성합니다.
  - `SessionCreationPolicy.STATELESS`
    - 세션을 생성하지 않으며, 애플리케이션이 이미 생성한 세션도 사용하지 않습니다.
    - Stateless 인증을 구현할 때 사용합니다.
    - JWT와 같은 토큰 기반 인증을 사용할 때 유용합니다.
    - `SecurityContextHolderFilter`는 세션 단위가 아닌 요청 단위로 항상 새로운 `SecurityContext`를 생성하므로 컨텍스트 영속성이 유지되지 않습니다.
---

## 🔍 중심 로직

```java
package org.springframework.security.config.http;

public enum SessionCreationPolicy {
    ALWAYS,
    NEVER,
    IF_REQUIRED,
    STATELESS;

    private SessionCreationPolicy() {
    }
}
```

```java
package org.springframework.security.web.session;

...

public class ForceEagerSessionCreationFilter extends OncePerRequestFilter {
    public ForceEagerSessionCreationFilter() {
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (this.logger.isDebugEnabled() && session.isNew()) {
            this.logger.debug(LogMessage.format("Created session eagerly", new Object[0]));
        }

        filterChain.doFilter(request, response);
    }
}
```

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.NEVER)
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }
}
```

---

### 주의

- `STATELESS` 설정에도 세션이 생성될 수 있습니다.
  - CSRF 기능이 활성화 되어 있으면 CSRF 토큰을 저장하기 위해 세션이 생성됩니다.
  - 세션은 생성되지만 CSRF 기능을 위해서만 사용되므로 `SecurityContext` 영속성에 영향을 미치지는 않습니다.

## 📂 참고할만한 자료
