# ☘️ 요청 기반 권한 부여 - HttpSecurity.authorizeHttpRequests() - 1 ~ 2

---

## 📖 내용

- Spring Security는 요청 기반 권한 부여 (Request Based Authorization)와 메소드 기반 권한 부여 (Method Based Authorization)를 통해 자원에 대한 심층적인 방어를 제공합니다.

- 요청 기반 권한 부여
  - `HttpSecurity.authorizeHttpRequests()` 메소드를 사용하여 요청에 대한 권한을 설정합니다.
  - 클라이언트의 요청 (HttpServletRequest)에 대한 권한 부여를 모델링 하는 것이며 이를 위해 HttpSecurity 인스턴스를 사용하여 권한 규칙을 선언할 수 있습니다.
  - `authorizeHttpRequests()`을 통해 권한 규칙이 설정되면 내부적으로 `AuthorizationFilter`가 요청에 대한 권한 검사 및 승인 작업을 수행합니다.

- `requestMatchers()`
  - `authorizeHttpRequests()`의 인자로 전달되는 `RequestMatcher`를 사용하여 요청을 매칭합니다.
- `requestMatchers() 종류`
  - `requestMatchers(String... urlPatterns)` : URL 패턴을 사용하여 요청을 매칭합니다.
  - `requestMatchers(RequestMatcher... requestMatchers)` : RequestMatcher를 사용하여 요청을 매칭합니다. `AntPathRequestMatcher`, `MvcRequestMatcher` 등의 구현체를 사용할 수 있습니다.
  - `requestMatchers(HttpMethod method, String... urlPatterns)` : HTTP 메소드와 URL 패턴을 사용하여 요청을 매칭합니다.

---

## 🔍 중심 로직

```java
package org.springframework.security.web.util.matcher;

...

public interface RequestMatcher {
    boolean matches(HttpServletRequest request);

    default MatchResult matcher(HttpServletRequest request) {
        boolean match = this.matches(request);
        return new MatchResult(match, Collections.emptyMap());
    }

    public static class MatchResult {
        private final boolean match;
        private final Map<String, String> variables;

        MatchResult(boolean match, Map<String, String> variables) {
            this.match = match;
            this.variables = variables;
        }

        public boolean isMatch() {
            return this.match;
        }

        public Map<String, String> getVariables() {
            return this.variables;
        }

        public static MatchResult match() {
            return new MatchResult(true, Collections.emptyMap());
        }

        public static MatchResult match(Map<String, String> variables) {
            return new MatchResult(true, variables);
        }

        public static MatchResult notMatch() {
            return new MatchResult(false, Collections.emptyMap());
        }
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
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user"). hasAuthority("USER") //엔드 포인트와 권한 설정, 요청이 /user 엔드포인트 요청인 경우 USER 권한을 필요로 한다
                        .requestMatchers("/mypage/**").hasAuthority("USER") //Ant 패턴을 사용할 수 있다. 요청이 /mypage 또는 하위 경로인 경우 USER 권한을 필요로 한다
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/resource/[A-Za-z0-9]+")).hasAuthority("USER") //정규 표현식을 사용할 수 있다
                        .requestMatchers(HttpMethod.GET, "/**").hasAuthority("read") //HTTP METHOD 를 옵션으로 설정할 수 있다
                                        .requestMatchers(HttpMethod.POST).hasAuthority("write") // POST 방식의 모든 엔드포인트 요청은 write 권한을 필요로 한다
                                        .requestMatchers(new AntPathRequestMatcher("/manager/**")).hasAuthority("MANAGER") //원하는 RequestMatcher 를 직접 사용할 수 있다
                                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN","MANAGER") // /admin/ 이하의 모든 요청은 ADMIN 과 MANAGER 권한을 필요로 한다
                        .anyRequest().authenticated()) // 위에서 정의한 규칙 외 모든 엔드포인트 요청은 인증을 필요로 한다
                .build();
    }
}
```

📌 주의
- 스프링 시큐리티는 클라이언트의 요청에 대하여 ***위에서 부터 아래로 나열된 순서대로 처리***하며 요청에 대하여 첫 번째 일치만 적용되고 다음 순서로 넘어가지 않습니다.
- 따라서 `/admin/**` 가 `/admin/db` 요청을 포함하므로 의도한 대로 권한 규칙이 올바르게 적용 되지 않을 수 있습니다. 
- 그렇기 때문에 엔드 포인트 설정 시 좁은 범위의 경로를 먼저 정의하고 그 것 보다 큰 범위의 경로를 다음 설정으로 정의 해야 합니다. 

---

## 📂 참고할만한 자료

