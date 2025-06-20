# ☘️ 메서드 기반 권한 부여 - @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter, @Secured, JSR-250

---

## 📖 내용
- 메소드 기반 권한 부여를 활성화 하기 위해서는 설정 클래스에 `@EnableMethodSecurity` 애노테이션을 추가해야 합니다.
- `@EnableMethodSecurity` 사용 시 아래 설정들이 활성화 됩니다.
  - `jsr250Enabled()`: JSR-250 관련 어노테이션들(@RolesAllowed, @PermitAll, @DenyAll) 을 활성화 합니다.
  - `securedEnabled()`: @Secured 어노테이션을 활성화 합니다.
  - `prePostEnabled()`: @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter 어노테이션을 활성화 합니다. (default: true)

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    ...
}
```

---

## 🔍 중심 로직

```java
package org.springframework.security.config.annotation.method.configuration;

...

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({MethodSecuritySelector.class})
public @interface EnableMethodSecurity {
    boolean prePostEnabled() default true;

    boolean securedEnabled() default false;

    boolean jsr250Enabled() default false;

    boolean proxyTargetClass() default false;

    AdviceMode mode() default AdviceMode.PROXY;

    int offset() default 0;
}
```

📌
- `@PreAuthorize`: 메소드가 실행되기 전에 조건이 충족되는지 검증합니다.
- `@PostAuthorize`: 메소드가 실행된 후 조건이 충족되는지 검증합니다.
- `@PreFilter`: 메소드가 실행되기 전에 메소드에 전달된 컬렉션 타입의 파라미터에 대한 필터링을 수행하는데 사용됩니다.
- `@PostFilter`: 메소드가 실행된 후 메소드가 반환하는 컬렉션 타입의 결과에 대해 필터링을 수행하는 데 사용됩니다.
- `@Secured`: 지정된 권한(역할)을 가진 사용자만 해당 메소드를 호출할 수 있지만 `@PreAuthorize`를 사용하는 것을 추천합니다.
  - `@Secured`를 사용하기 위해서는 `@EnableMethodSecurity(securedEnabled = true)`를 설정해야 합니다.
- `JSR-250(@RolesAllowed, @PermitAll, @DenyAll)`
  - `JSR-250 관련 애노테이션들`을 사용하기 위해서는 `@EnableMethodSecurity(jsr250Enabled = true)`를 설정해야 합니다.

- 이 외에도 메타 주석, 커스텀 빈등을 활용하여 커스텀 권한 부여를 구현할 수 있습니다.
---

## 📂 참고할만한 자료

