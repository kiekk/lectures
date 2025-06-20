# ☘️ 프로젝트 생성 / 의존성 추가

---

## 📖 내용

- 프로젝트를 처음 생성하여 기본 설정과 동작 방식을 이해합니다.
- 별도의 커스텀 설정을 하지 않을 경우 `org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration.SecurityFilterChainConfiguration` 클래스 에서 `defaultSecurityFilterChain`이 Bean으로 등록됩니다.
- 커스텀 설정의 여부는 `@ConditionalOnDefaultWebSecurity` 애노테이션을 통해 확인합니다.
---

## 🔍 중심 로직

```java
package org.springframework.boot.autoconfigure.security;

...

@ConfigurationProperties(
    prefix = "spring.security"
)
public class SecurityProperties {
    public static final int BASIC_AUTH_ORDER = 2147483642;
    public static final int IGNORED_ORDER = Integer.MIN_VALUE;
    public static final int DEFAULT_FILTER_ORDER = -100;
    private final Filter filter = new Filter();
    private final User user = new User();

    public SecurityProperties() {
    }

  ... getter & setter

    public static class Filter {
        private int order = -100;
        private Set<DispatcherType> dispatcherTypes;

        public Filter() {
            this.dispatcherTypes = new HashSet(Arrays.asList(DispatcherType.ASYNC, DispatcherType.ERROR, DispatcherType.REQUEST));
        }

      ... getter & setter
    }

    public static class User {
        private String name = "user";
        private String password = UUID.randomUUID().toString();
        private List<String> roles = new ArrayList();
        private boolean passwordGenerated = true;

        public User() {
        }

      ... getter & setter
    }
}
```

```java
package org.springframework.boot.autoconfigure.security;

...

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({DefaultWebSecurityCondition.class})
public @interface ConditionalOnDefaultWebSecurity {
}
```

```java
package org.springframework.boot.autoconfigure.security;

...

class DefaultWebSecurityCondition extends AllNestedConditions {
  DefaultWebSecurityCondition() {
    super(ConfigurationPhase.REGISTER_BEAN);
  }

  // classpath에 SecurityFilterChain, HttpSecurity가 존재하는지 확인
  // Spring Security 의존성을 추가할 경우 해당 클래스들은 자동으로 제공되기 때문에 무조건 true
  @ConditionalOnClass({SecurityFilterChain.class, HttpSecurity.class})
  static class Classes {
    Classes() {
    }
  }

  // WebSecurityConfigurerAdapter, SecurityFilterChain타입으로 등록된 Bean이 없는지 확인
  // 커스텀 설정을 적용할 경우 해당 Bean을 직접 등록하기 때문에 false
  // 커스텀 설정을 적용하지 않을 경우 true
  @ConditionalOnMissingBean({WebSecurityConfigurerAdapter.class, SecurityFilterChain.class})
  static class Beans {
    Beans() {
    }
  }
}
```

```java

package org.springframework.boot.autoconfigure.security.servlet;

...

@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnWebApplication(
    type = Type.SERVLET
)
class SpringBootWebSecurityConfiguration {
    SpringBootWebSecurityConfiguration() {
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnDefaultWebSecurity // 해당 애노테이션의 조건을 만족할 경우에만 이 클래스가 활성화됨
    static class SecurityFilterChainConfiguration {
        SecurityFilterChainConfiguration() {
        }

        @Bean
        @Order(2147483642)
        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().anyRequest()).authenticated(); // 모든 요청에 대해 인증을 요구함
            http.formLogin(); // 기본값 설정
            http.httpBasic(); // 기본값 설정
            return (SecurityFilterChain)http.build();
        }
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnClass({WebInvocationPrivilegeEvaluator.class})
    @ConditionalOnBean({WebInvocationPrivilegeEvaluator.class})
    static class ErrorPageSecurityFilterConfiguration {
        ErrorPageSecurityFilterConfiguration() {
        }

        @Bean
        FilterRegistrationBean<ErrorPageSecurityFilter> errorPageSecurityFilter(ApplicationContext context) {
            FilterRegistrationBean<ErrorPageSecurityFilter> registration = new FilterRegistrationBean(new ErrorPageSecurityFilter(context), new ServletRegistrationBean[0]);
            registration.setDispatcherTypes(DispatcherType.ERROR, new DispatcherType[0]);
            return registration;
        }
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnMissingBean(
        name = {"springSecurityFilterChain"}
    )
    @ConditionalOnClass({EnableWebSecurity.class})
    @EnableWebSecurity
    static class WebSecurityEnablerConfiguration {
        WebSecurityEnablerConfiguration() {
        }
    }
}

```

📌  요약
- `SpringBootWebSecurityConfiguration` 클래스의 `SecurityFilterChainConfiguration` 클래스에서
Security 관련 커스텀 설정 이 없을 경우 기본적으로 `SecurityFilterChain`을 Bean으로 등록하는데,
- 이때 `@ConditionalOnDefaultWebSecurity` 애노테이션을 통해 조건(Custom 설정 여부)을 확인합니다.

---

## 💬 코멘트
- Spring Security가 기본적으로 제공하는 `SecurityFilterChain`을 사용하여 기본적인 보안 설정을 적용합니다.
- 별도의 설정 없이도 기본 설정을 자동으로 적용해준다는 것은 알았지만, 정확히 어떤 객체가 어떤 방식을 통해 제공하는지를 확인해봤습니다.

---
