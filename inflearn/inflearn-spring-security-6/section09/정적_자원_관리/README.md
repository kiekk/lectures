# ☘️ 정적 자원 관리

---

## 📖 내용
- Spring Security에서는 `RequestMatcher`를 사용하여 무시해야 할 요청을 지정할 수 있습니다.
- 주로 정적 자원(이미지, CSS, JavaScript 파일 등)에 대한 요청이나 특정 엔드포인트가 보안 필터를 거치지 않도록 설정할 때 사용됩니다.
- Spring Security는 `StaticResourceLocation` 열거형을 제공하여 기본 정적 자원 위치를 정의합니다.

---

## 🔍 중심 로직

```java
package org.springframework.boot.autoconfigure.security.reactive;

public final class PathRequest {
    private PathRequest() {
    }

    public static StaticResourceRequest toStaticResources() {
        return StaticResourceRequest.INSTANCE;
    }
}
```

```java
package org.springframework.boot.autoconfigure.security.reactive;

...

public final class StaticResourceRequest {
    static final StaticResourceRequest INSTANCE = new StaticResourceRequest();

    private StaticResourceRequest() {
    }

    public StaticResourceServerWebExchange atCommonLocations() {
        return this.at(EnumSet.allOf(StaticResourceLocation.class));
    }

    public StaticResourceServerWebExchange at(StaticResourceLocation first, StaticResourceLocation... rest) {
        return this.at(EnumSet.of(first, rest));
    }

    public StaticResourceServerWebExchange at(Set<StaticResourceLocation> locations) {
        Assert.notNull(locations, "Locations must not be null");
        return new StaticResourceServerWebExchange(new LinkedHashSet(locations));
    }
    
    ... other methods
}
```

```java
package org.springframework.boot.autoconfigure.security;

...

public enum StaticResourceLocation {
    CSS(new String[]{"/css/**"}),
    JAVA_SCRIPT(new String[]{"/js/**"}),
    IMAGES(new String[]{"/images/**"}),
    WEB_JARS(new String[]{"/webjars/**"}),
    FAVICON(new String[]{"/favicon.*", "/*/icon-*"});

    private final String[] patterns;

    private StaticResourceLocation(String... patterns) {
        this.patterns = patterns;
    }

    public Stream<String> getPatterns() {
        return Arrays.stream(this.patterns);
    }
}
```

```java
// Spring Security 6.0 이전
@Bean
public WebSecurityCustomizer webSecurityCustomizer() {
    return (webSecurity) -> {
        webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    };
}

// Spring Security 6.0 이후
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/images/**", "/js/**", "/webjars/**", "/favicon.*", "/*/icon-*").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        .anyRequest().authenticated())
        .build();
    }
}
```

📌
- Spring Security 6.0 부터는 `webSecurityCustomizer` 대신 `http.authorizeHttpRequests()의 permitAll()` 사용을 권장하고 있습니다.
  - `You are asking Spring Security to ignore Ant [pattern='...']. This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.`

---

## 📂 참고할만한 자료

