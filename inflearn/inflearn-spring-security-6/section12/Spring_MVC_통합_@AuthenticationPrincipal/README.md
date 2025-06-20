# ☘️ Spring MVC 비동기 통합 - WebAsyncManagerIntegrationFilter

---

## 📖 내용
- Spring Security는 Spring MVC에서 현재 `Authentication.getPrincipal()`을 자동으로 해결할 수 있는 `AuthenticationPrincipalArgumentResolver`를 제공합니다.
- `@AuthenticationPrincipal`을 메서드 인수에 선언하게 되면 Spring Security와 독립적으로 사용할 수 있습니다.
- `@AuthenticationPrincipal`을 사용하면 `AuthenticationPrincipalArgumentResolver`가 메서드 호출 전 가로 채어 처리합니다.

### Authentication.getPrincipal()
```java
@RequestMapping("/user")
public void findUser() {
    Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
    CustomUser custom = (CustomUser) authentication == null ? null : authentication.getPrincipal();
}

@RequestMapping("/user")
public Customer findUser(@AuthenticationPrincipal CustomUser customUser) {}
```

### Tip
- `@AuthenticationPrincipal`을 메타 주석으로 사용하여 보다 유연하게 사용할 수도 있습니다.
```java
@Target({ ElementType.PARAMETER, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal(expression = "{표현식 작성}")
public @interface CurrentUser {}

@RequestMapping("/user")
public void user(@CurrentUser CustomUser customUser) {}
```

---

## 🔍 중심 로직

```java
package org.springframework.security.web.method.annotation;

...

public final class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final Class<AuthenticationPrincipal> annotationType = AuthenticationPrincipal.class;
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private ExpressionParser parser = new SpelExpressionParser();
    private SecurityAnnotationScanner<AuthenticationPrincipal> scanner = SecurityAnnotationScanners.requireUnique(AuthenticationPrincipal.class);
    private boolean useAnnotationTemplate = false;
    private BeanResolver beanResolver;

    public AuthenticationPrincipalArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return this.findMethodAnnotation(parameter) != null;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication authentication = this.securityContextHolderStrategy.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        } else {
            Object principal = authentication.getPrincipal();
            AuthenticationPrincipal annotation = this.findMethodAnnotation(parameter);
            String expressionToParse = annotation.expression();
            if (StringUtils.hasLength(expressionToParse)) {
                StandardEvaluationContext context = new StandardEvaluationContext();
                context.setRootObject(principal);
                context.setVariable("this", principal);
                context.setBeanResolver(this.beanResolver);
                Expression expression = this.parser.parseExpression(expressionToParse);
                principal = expression.getValue(context);
            }

            if (principal != null && !ClassUtils.isAssignable(parameter.getParameterType(), principal.getClass())) {
                if (annotation.errorOnInvalidType()) {
                    String var10002 = String.valueOf(principal);
                    throw new ClassCastException(var10002 + " is not assignable to " + String.valueOf(parameter.getParameterType()));
                } else {
                    return null;
                }
            } else {
                return principal;
            }
        }
    }

    public void setBeanResolver(BeanResolver beanResolver) {
        this.beanResolver = beanResolver;
    }

    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        Assert.notNull(securityContextHolderStrategy, "securityContextHolderStrategy cannot be null");
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    public void setTemplateDefaults(AnnotationTemplateExpressionDefaults templateDefaults) {
        this.scanner = SecurityAnnotationScanners.requireUnique(AuthenticationPrincipal.class, templateDefaults);
        this.useAnnotationTemplate = templateDefaults != null;
    }

    private AuthenticationPrincipal findMethodAnnotation(MethodParameter parameter) {
        if (this.useAnnotationTemplate) {
            return (AuthenticationPrincipal)this.scanner.scan(parameter.getParameter());
        } else {
            AuthenticationPrincipal annotation = (AuthenticationPrincipal)parameter.getParameterAnnotation(this.annotationType);
            if (annotation != null) {
                return annotation;
            } else {
                Annotation[] annotationsToSearch = parameter.getParameterAnnotations();

                for(Annotation toSearch : annotationsToSearch) {
                    annotation = (AuthenticationPrincipal)AnnotationUtils.findAnnotation(toSearch.annotationType(), this.annotationType);
                    if (annotation != null) {
                        return (AuthenticationPrincipal)MergedAnnotations.from(new Annotation[]{toSearch}).get(this.annotationType).synthesize();
                    }
                }

                return null;
            }
        }
    }
}
```

📌
- Spring Security 사용 초창기에는 `@AuthenticationPrincipal`, 그리고 메타 주석을 활용한 애노테이션 방식을 자주 사용했는데,
- 최근에는 메서드에 파라미터를 계속 추가해줘야 한다는 것이 단점으로 느껴져 `SecurityContextHolder.getContext().getAuthentication()`을 사용하여 직접 꺼내서 사용하는 경우가 많습니다.
- 대신 비즈니스 로직에서 매번 직접 호출하지 않고 util 클래스를 만들어서 사용합니다.

---

## 📂 참고할만한 자료

