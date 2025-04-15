# ☘️ 요청 기반 Custom AuthorizationManager 구현

---

## 📖 내용
- Spring Security에서 `HttpSecurity.access()` 메서드를 사용해 인가 설정을 하는데 이 때 `AuthorizationManager`를 사용합니다.
- `AuthorizationManager`를 직접 구현하여 요청 기반 인가를 처리할 수 있습니다.

---

## 🔍 중심 로직

```java
package com.inflearn.security.manager;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final String REQUIRED_ROLE = "ROLE_SECURE";

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Authentication auth = authentication.get();
        boolean granted = isAuthenticated(auth) && hasRequiredRole(auth);
        return new AuthorizationDecision(granted);
    }

    @Override
    public AuthorizationResult authorize(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        return this.check(authentication, object);
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    private static boolean hasRequiredRole(Authentication auth) {
        // "ROLE_SECURE" 권한을 가진 사용자인지 확인
        return auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> REQUIRED_ROLE.equals(grantedAuthority.getAuthority()));
    }
}
```

📌
- `check()` 메서드는 deprecated이므로 `authorize()` 메서드를 사용해야 합니다. (`check()` 메서드가 아직 삭제되지 않았기 때문에 아직까지는 구현을 해야 합니다.)

---

## 📂 참고할만한 자료

