# ☘️ 인가 이벤트 - Authorization Events

---

## 📖 내용
- 인가 관련 이벤트를 수신하려면 `ApplicationEventPublisher`를 사용하거나 Spring Security에서 제공하는 `AuthorizationEventPublisher`를 구현하여 사용합니다.
- 기본적으로 `AuthorizationEventPublisher` 구현체로 `SpringAuthorizationEventPublisher`가 제공됩니다.`

### 이벤트 발행 방법
```java
ApplicationEventPublisher.publishEvent(ApplicationEvent)
AuthorizationEventPublisher.publishAuthorizationEvent(Supplier<Authentication>, T, AuthorizationDecision)
```

### 이벤트 수신 방법
```java
@Component
public class AuthorizationEvents {
    @EventListener
    public void onAuthorization(AuthorizationDeniedEvent failure){}
    
    @EventListener
    public void onAuthorization(AuthorizationGrantedEvent success){}
}
```

- 인가 이벤트를 발행하기 위해서는 `SpringAuthorizationEventPublisher`를 빈으로 정의해야 합니다.

---

## 🔍 중심 로직

```java
package org.springframework.security.authorization;

...

@FunctionalInterface
public interface AuthorizationEventPublisher {
    /** @deprecated */
    @Deprecated
    <T> void publishAuthorizationEvent(Supplier<Authentication> authentication, T object, AuthorizationDecision decision);

    default <T> void publishAuthorizationEvent(Supplier<Authentication> authentication, T object, AuthorizationResult result) {
        if (result == null) {
            this.publishAuthorizationEvent(authentication, object, (AuthorizationDecision)null);
        } else if (result instanceof AuthorizationDecision) {
            AuthorizationDecision decision = (AuthorizationDecision)result;
            this.publishAuthorizationEvent(authentication, object, decision);
        } else {
            throw new UnsupportedOperationException("result must be of type AuthorizationDecision");
        }
    }
}
```

```java
package org.springframework.security.authorization;

...

public final class SpringAuthorizationEventPublisher implements AuthorizationEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public SpringAuthorizationEventPublisher(ApplicationEventPublisher eventPublisher) {
        Assert.notNull(eventPublisher, "eventPublisher cannot be null");
        this.eventPublisher = eventPublisher;
    }

    public <T> void publishAuthorizationEvent(Supplier<Authentication> authentication, T object, AuthorizationDecision decision) {
        this.publishAuthorizationEvent(authentication, object, (AuthorizationResult)decision);
    }

    public <T> void publishAuthorizationEvent(Supplier<Authentication> authentication, T object, AuthorizationResult result) {
        if (result != null && !result.isGranted()) {
            AuthorizationDeniedEvent<T> failure = new AuthorizationDeniedEvent(authentication, object, result);
            this.eventPublisher.publishEvent(failure);
        }
    }
}
```

### 이벤트 발행
```java
@Bean
public AuthorizationEventPublisher authorizationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    return new SpringAuthorizationEventPublisher(applicationEventPublisher);
}
```

### 이벤트 수신
```java
@Component
public class AuthorizationEvents {
    @EventListener
    public void onAuthorization(AuthorizationEvent event) {
        System.out.println("failure = " + event.getAuthentication().get().getAuthorities());
    }

    @EventListener
    public void onAuthorization(AuthorizationDeniedEvent failure) {
        System.out.println("failure = " + failure.getAuthentication().get().getAuthorities());
    }

    @EventListener
    public void onAuthorization(AuthorizationGrantedEvent success) {
        System.out.println("success = " + success.getAuthentication().get().getAuthorities());
    }
}
```

📌

---

## 📂 참고할만한 자료

