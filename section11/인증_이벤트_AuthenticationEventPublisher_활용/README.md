# ☘️ 인증 이벤트 - AuthenticationEventPublisher 활용

---

## 📖 내용
- `AuthenticationEventPublisher`를 활용하여 커스텀 예외, 그리고 커스텀 예외에 대한 처리를 담당하는 event를 등록할 수 있습니다.

---

## 🔍 중심 로직

### 커스텀 예외, 커스텀 예외에 대한 이벤트 처리 등록
```java
@Bean
public AuthenticationProvider customAuthenticationProvider() {
    return new CustomAuthenticationProvider(customAuthenticationEventPublisher(null));
}

@Bean
public AuthenticationEventPublisher customAuthenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    Map<Class<? extends AuthenticationException>, Class<? extends AbstractAuthenticationFailureEvent>> mapping =
            Collections.singletonMap(CustomException.class, CustomAuthenticationFailureEvent.class);

    DefaultAuthenticationEventPublisher authenticationEventPublisher = new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    authenticationEventPublisher.setAdditionalExceptionMappings(mapping); // CustomException 을 던지면 CustomAuthenticationFailureEvent 를 발행하도록 추가 함
    return authenticationEventPublisher;
}

// CustomAuthenticationFailureEvent
public class CustomAuthenticationFailureEvent extends AbstractAuthenticationFailureEvent {

    public CustomAuthenticationFailureEvent(Authentication authentication, AuthenticationException exception) {
        super(authentication, exception);
    }
}
```

### 기본 이벤트 설정
```java
@Bean
public AuthenticationEventPublisher customAuthenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    
    ...
    
    authenticationEventPublisher.setDefaultAuthenticationFailureEvent(DefaultAuthenticationFailureEvent.class); // 기본 이벤트 설정
    return authenticationEventPublisher;
}

// DefaultAuthenticationFailureEvent
public class DefaultAuthenticationFailureEvent extends AbstractAuthenticationFailureEvent {

    public DefaultAuthenticationFailureEvent(Authentication authentication, AuthenticationException exception) {
        super(authentication, exception);
    }
}
```

📌

---

## 📂 참고할만한 자료

