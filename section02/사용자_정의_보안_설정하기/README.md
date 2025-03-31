# ☘️ 사용자 정의 보안 설정하기

---

## 📖 내용
- `SecurityFilterChain` 빈을 직접 등록하여 사용자 정의 보안 설정을 할 수 있습니다.
- application.yml 에 Security 관련 property 값도 설정할 수 있습니다.

---

## 🔍 중심 로직

```java
 @Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) // 모든 요청에 대해 인증
                .formLogin(Customizer.withDefaults()) // form Login 활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
                .build();
    }

    // application.yml 또는 java class를 통해 사용자 정의
    // 1. java class
    // 2. application.yml 순으로 설정이 적용된다.
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user = User.withUsername("user")
                .password("{noop}1111")
                .authorities("ROLE_USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
```

```yaml
spring:
  security:
    user:
      name: user2
      password: 1111
      roles: USER
```

📌  요약
- `SecurityFilterChain` 빈을 직접 등록하여 사용자 정의 보안 설정을 할 수 있습니다.
- application.yml 에 Security 관련 property 값도 설정할 수 있습니다.
- java class를 통해 사용자를 정의할 경우 application.yml에 적용한 사용자 정보는 무시됩니다.

---
