# ☘️ 인증 이벤트 - Authentication Events

---

## 📖 내용
- Spring Security는 인증이 성공하거나 실패하면 `AuthenticationSuccessEvent` 또는 `AuthenticationFailureEvent`를 발생시킵니다.
- 이벤트를 수신하려면 `ApplicationEventPublisher`를 사용하거나 Spring Security에서 제공하는 `AuthenticationEventPublisher`를 구현하여 사용합니다.
- 기본적으로 `AuthenticationEventPublisher` 구현체로 `DefaultAuthenticationEventPublisher`가 제공됩니다.

### 이벤트 발행 방법
```java
ApplicationEventPublisher.publishEvent(ApplicationEvent)
AuthenticationEventPublisher.publishAuthenticationSuccess(Authentication)
AuthenticationEventPublisher.publishAuthenticationFailure(AuthenticationException, Authentication)
```

### 이벤트 수신 방법
```java
@Component
public class AuthenticationEvents {
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {}
    
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {}
}
```

---

## 🔍 중심 로직

```java
package org.springframework.security.authentication.event;

...

public class AuthenticationSuccessEvent extends AbstractAuthenticationEvent {
    private static final long serialVersionUID = 2537206344128673963L;

    public AuthenticationSuccessEvent(Authentication authentication) {
        super(authentication);
    }
}
```

```java
package org.springframework.security.authentication.event;

...

public abstract class AbstractAuthenticationFailureEvent extends AbstractAuthenticationEvent {
    private final AuthenticationException exception;

    public AbstractAuthenticationFailureEvent(Authentication authentication, AuthenticationException exception) {
        super(authentication);
        Assert.notNull(exception, "AuthenticationException is required");
        this.exception = exception;
    }

    public AuthenticationException getException() {
        return this.exception;
    }
}
```

```java
package org.springframework.security.authentication;

...

public interface AuthenticationEventPublisher {
    void publishAuthenticationSuccess(Authentication authentication);

    void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication);
}
```

```java
package org.springframework.security.authentication;

...

public class DefaultAuthenticationEventPublisher implements AuthenticationEventPublisher, ApplicationEventPublisherAware {
    private final Log logger;
    private ApplicationEventPublisher applicationEventPublisher;
    private final HashMap<String, Constructor<? extends AbstractAuthenticationEvent>> exceptionMappings;
    private Constructor<? extends AbstractAuthenticationFailureEvent> defaultAuthenticationFailureEventConstructor;

    public DefaultAuthenticationEventPublisher() {
        this((ApplicationEventPublisher)null);
    }

    public DefaultAuthenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.logger = LogFactory.getLog(this.getClass());
        this.exceptionMappings = new HashMap();
        this.applicationEventPublisher = applicationEventPublisher;
        this.addMapping(BadCredentialsException.class.getName(), AuthenticationFailureBadCredentialsEvent.class);
        this.addMapping(UsernameNotFoundException.class.getName(), AuthenticationFailureBadCredentialsEvent.class);
        this.addMapping(AccountExpiredException.class.getName(), AuthenticationFailureExpiredEvent.class);
        this.addMapping(ProviderNotFoundException.class.getName(), AuthenticationFailureProviderNotFoundEvent.class);
        this.addMapping(DisabledException.class.getName(), AuthenticationFailureDisabledEvent.class);
        this.addMapping(LockedException.class.getName(), AuthenticationFailureLockedEvent.class);
        this.addMapping(AuthenticationServiceException.class.getName(), AuthenticationFailureServiceExceptionEvent.class);
        this.addMapping(CredentialsExpiredException.class.getName(), AuthenticationFailureCredentialsExpiredEvent.class);
        this.addMapping("org.springframework.security.authentication.cas.ProxyUntrustedException", AuthenticationFailureProxyUntrustedEvent.class);
        this.addMapping("org.springframework.security.oauth2.server.resource.InvalidBearerTokenException", AuthenticationFailureBadCredentialsEvent.class);
    }

    public void publishAuthenticationSuccess(Authentication authentication) {
        if (this.applicationEventPublisher != null) {
            this.applicationEventPublisher.publishEvent(new AuthenticationSuccessEvent(authentication));
        }

    }

    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        Constructor<? extends AbstractAuthenticationEvent> constructor = this.getEventConstructor(exception);
        AbstractAuthenticationEvent event = null;
        if (constructor != null) {
            try {
                event = (AbstractAuthenticationEvent)constructor.newInstance(authentication, exception);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException var6) {
            }
        }

        if (event != null) {
            if (this.applicationEventPublisher != null) {
                this.applicationEventPublisher.publishEvent(event);
            }
        } else if (this.logger.isDebugEnabled()) {
            this.logger.debug("No event was found for the exception " + exception.getClass().getName());
        }

    }

    ... other methods
}
```

📌

---

## 📂 참고할만한 자료

