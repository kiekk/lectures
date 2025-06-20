# ☘️ AOP 메서드 보안 구현하기 - MethodInterceptor, Pointcut, Advisor

---

## 📖 내용
- 지금까지 Spring Security에서 메소드 기반 인가 처리를 위해 AOP를 사용하는 것을 알아봤습니다.
- 그렇다면 결국 Spring Security를 사용하지 않고도 AOP만을 사용해서 메서드 보안을 구현할 수 있다는 말이 되기 때문에 직접 AOP의 기능들을 사용하여 메서드 보안을 구현해봅니다.


---

## 🔍 중심 로직

```java
@Configuration
public class AopConfig {
    @Bean
    public MethodInterceptor customMethodInterceptor() {
        AuthorizationManager<MethodInvocation> authorizationManager = AuthenticatedAuthorizationManager.authenticated();
        return new CustomMethodInterceptor(authorizationManager); // AOP 어라운드 어드바이스를 선언한다
    }

    @Bean
    public Pointcut servicePointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* io.security.Myservice.*(..))"); // AOP 수행 대상 클래스와 대상 메소드를 지정한다
        return pointcut;
    }

    @Bean
    public Advisor serviceAdvisor(MethodInterceptor customMethodInterceptor, Pointcut servicePointcut) { // 초기화 시 Advisor 목록에 포함된다
        return new DefaultPointcutAdvisor(servicePointcut, customMethodInterceptor);
    }
}
```

```java
package com.inflearn.security.aop;

...

import java.util.Objects;

public class CustomMethodInterceptor implements MethodInterceptor {

    private final AuthorizationManager<MethodInvocation> authorizationManager;

    public CustomMethodInterceptor(AuthorizationManager<MethodInvocation> authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.requireNonNull(authorizationManager.authorize(() -> authentication, invocation)).isGranted()) {
            return invocation.proceed(); // 실제 대상 객체를 호출한다
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
```

📌

---

## 📂 참고할만한 자료

