# ☘️ 메서드 기반 Custom AuthorizationManager 구현

---

## 📖 내용
- 커스텀 `AuthorizationManager`를 구현하여 메서드 기반 인가를 처리합니다.
- 이 때 Spring Security에서 제공하는 `AuthorizationManager`를 비활성화 하지 않으면 중복 검사하게 되므로 
  `@EnableGlobalMethodSecurity(prePostEnabled = false)`를 설정합니다.

- 인터셉터간에는 `AuthorizationInterceptorsOrder`를 사용하여 순서를 지정할 수 있습니다.
- 만약 메소드 인가 어드바이스가 실행되기 전에 트랜잭션을 시작하고 싶은 경우 `@EnableTransactionManagement`의 순서를 설정해야 합니다.
- ex) `@EnableTransactionManagement(order = 0)`

---

## 🔍 중심 로직

```java
package org.springframework.security.authorization.method;

public enum AuthorizationInterceptorsOrder {
    FIRST(Integer.MIN_VALUE),
    PRE_FILTER,
    PRE_AUTHORIZE,
    SECURED,
    JSR250,
    SECURE_RESULT(450),
    POST_AUTHORIZE(500),
    POST_FILTER(600),
    LAST(Integer.MAX_VALUE);

    private static final int INTERVAL = 100;
    private final int order;

    private AuthorizationInterceptorsOrder() {
        this.order = this.ordinal() * 100;
    }

    private AuthorizationInterceptorsOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }
}
```

📌

---

## 📂 참고할만한 자료

