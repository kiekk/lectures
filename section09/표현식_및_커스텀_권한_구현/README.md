# ☘️ 표현식 및 커스텀 권한 구현

---

## 📖 내용
- Spring Security는 `WebExpressionAuthorizationManager`을 사용하여 표현식 기반 권한 부여를 지원합니다.

---

## 🔍 중심 로직

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/resource/{name}").access(new WebExpressionAuthorizationManager("#name == authentication.name"))
                        .requestMatchers("/admin/db").access(new WebExpressionAuthorizationManager("hasAuthority('DB') or hasRole('ADMIN')"))
                        .requestMatchers("/admin/db").access(anyOf(hasAuthority("db"), hasRole("ADMIN")))
                )
                .build();
    }
}
```

📌 
- `WebExpressionAuthorizationManager`를 사용해 url에서 값을 추출하여 표현식에 사용할 수 있으며 여러 개의 권한 규칙 설정도 적용할 수 있습니다.


### 커스텀 권한 표현식 적용
```java
SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, ApplicationContext context) throws Exception {
    DefaultHttpSecurityExpressionHandler expressionHandler = new DefaultHttpSecurityExpressionHandler();
    expressionHandler.setApplicationContext(context);
    
    // bean 이름으로 참조 설정
    WebExpressionAuthorizationManager expressManager = new WebExpressionAuthorizationManager("@customWebSecurity.check(authentication,request)");
    expressManager.setExpressionHandler(expressionHandler);
    
    http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/resource/**").access(expressManager));
    return http.build();
}

@Component("customWebSecurity")
public class CustomWebSecurity {
    public boolean check(Authentication authentication, HttpServletRequest request) {
        return authentication.isAuthenticated(); // 사용자가 인증되었는지를 검사
    }
}
```

📌
- 이 방법은 실제 사용해본 적은 없고 앞으로도 사용할 수 있을지 잘 모르겠지만 혹시 모르니 참고용으로 기록해둡니다.
- `access`는 주로 요청 기반 권한 부여 보다는 메소드 기반 권한 부여 방식에서 많이 사용했습니다.

---

## 📂 참고할만한 자료

