# ☘️ CORS (Cross Origin Resource Sharing) - 1 ~ 2

---

## 📖 내용
- `CORS(Cross Origin Resource Sharing)`
  - 다른 출처의 리소스를 안전하게 사용하고자 할 경우 CORS가 등장하며 CORS는 특별한 HTTP 헤더를 통해 웹 사이트가 다른 출처의 리소스에 접근할 수 있도록 '허가'를 구하는 방법이라고 할 수 있습니다.
  - 리소스 출처를 비교하는 로직은 서버에 구현된 스펙이 아닌 브라우저에 구현된 스펙 기준으로 처리되며 브라우저는 클라이언트의 요청 헤더와 서버의 응답 헤더를 비교해서 최종 응답을 결정합니다.
  - 출처 비교 방법은 URL 구성요소 중 `Protocol`, `Host`, `Port`를 비교하여 결정합니다.

<img src="https://mdn.github.io/shared-assets/images/diagrams/http/cors/fetching-page-cors.svg" width="500"/>

<sub>※ 이미지 출처: [https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS)</sub>
 
- `CORS` 종류
  - `Simple Request`: `Simple Request`는 예비 요청(Preflight) 과정 없이 자동으로 CORS가 동작하여 서버에 본 요청을 한 후, 서버가 응답 헤더에 `Access-Control-Allow-Origin`을 포함하여 응답하는 방식입니다.
    - Method 제한: GET, POST, HEAD 중 한가지 Method만 사용 가능
    - Content-Type 제한: application/x-www-form-urlencoded, multipart/form-data, text/plain 중 한가지 Content-Type만 사용 가능
  - `Preflight Request`: 본 요청을 보내기 전 예비 요청을 보내며 Method는 `OPTIONS`가 사용됩니다.
    - 요청 사양이 `Simple Request`에 해당하지 않는 경우 `Preflight Request`을 실행합니다.

- `CORS` 설정
  - `Access-Control-Allow-Origin`: 헤더에 작성된 출처만 브라우저가 리소스에 접근할 수 있도록 허용합니다.
  - `Access-Control-Allow-Methods`: `Preflight Request`에 대한 응답으로 실제 요청 중에 사용할 수 있는 메서드를 나타냅니다.
  - `Access-Control-Allow-Headers`: `Preflight Request`에 대한 응답으로 실제 요청 중에 사용할 수 있는 헤더 필드를 나타냅니다.
  - `Access-Control-Allow-Credentials`: 실제 요청에 쿠키나 인증 등의 사용자 자격 증명이 포함될 수 있음을 나타냅니다. Client credentials: include 옵션일 경우 true 필수
  - `Access-Control-Max-Age`: `Preflight Request` 요청 결과를 캐시할 수 있는 시간을 나타내는 것으로 해당 시간 동안은 `Preflight Request`를 재요청 하지 않게 됩니다.

- `CORS` 동일 출처 기준

| URL                                     | 동일 출처 | 근거                          |
|-----------------------------------------|-------|-----------------------------|
| https://security.io/auth?username=user1 | O     | 스킴, 호스트, 포트가 동일             |
| https://user:password@security.io       | O     | 스킴, 호스트, 포트가 동일             |
| http://security.io                      | X     | 스킴이 다름                      |
| https://api.security.io                 | X     | 호스트가 다름                     |
| https://security.io:8000                | X     | 포트가 다름, explorer는 포트 자체를 무시 |

- `HttpSecurity.cors()` 메소드를 통해 활성화 할 수 있으며 `CorsConfigurer` 객체가 초기화 작업을 담당합니다.

---

## 🔍 중심 로직

```java
@Bean
SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.cors(cors -> cors.configurationSource(corsConfigurationSource())); 
    // 커스텀하게 사용할 CorsConfigurationSource 를 설정한다.
    // CorsConfigurationSource 를 설정하지 않으면 Spring MVC 의 CORS 구성을 사용한다
    return http.build();
}

@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("https://example.com");
    configuration.addAllowedMethod("GET","POST");
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

```java
package org.springframework.security.config.annotation.web.configurers;

...

public class CorsConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<CorsConfigurer<H>, H> {
    private static final String CORS_CONFIGURATION_SOURCE_BEAN_NAME = "corsConfigurationSource";
    private static final String CORS_FILTER_BEAN_NAME = "corsFilter";
    private static final String HANDLER_MAPPING_INTROSPECTOR = "org.springframework.web.servlet.handler.HandlerMappingIntrospector";
    private static final boolean mvcPresent = ClassUtils.isPresent("org.springframework.web.servlet.handler.HandlerMappingIntrospector", CorsConfigurer.class.getClassLoader());
    private CorsConfigurationSource configurationSource;

    public CorsConfigurer() {
    }

    public CorsConfigurer<H> configurationSource(CorsConfigurationSource configurationSource) {
        this.configurationSource = configurationSource;
        return this;
    }

    public void configure(H http) {
        ApplicationContext context = (ApplicationContext)http.getSharedObject(ApplicationContext.class);
        CorsFilter corsFilter = this.getCorsFilter(context);
        Assert.state(corsFilter != null, () -> "Please configure either a corsFilter bean or a corsConfigurationSourcebean.");
        http.addFilter(corsFilter);
    }

    private CorsFilter getCorsFilter(ApplicationContext context) {
        if (this.configurationSource != null) {
            return new CorsFilter(this.configurationSource);
        } else {
            boolean containsCorsFilter = context.containsBeanDefinition("corsFilter");
            if (containsCorsFilter) {
                return (CorsFilter)context.getBean("corsFilter", CorsFilter.class);
            } else {
                boolean containsCorsSource = context.containsBean("corsConfigurationSource");
                if (containsCorsSource) {
                    CorsConfigurationSource configurationSource = (CorsConfigurationSource)context.getBean("corsConfigurationSource", CorsConfigurationSource.class);
                    return new CorsFilter(configurationSource);
                } else {
                    return mvcPresent ? CorsConfigurer.MvcCorsFilter.getMvcCorsFilter(context) : null;
                }
            }
        }
    }

    static class MvcCorsFilter {
        private static final String HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME = "mvcHandlerMappingIntrospector";

        MvcCorsFilter() {
        }

        private static CorsFilter getMvcCorsFilter(ApplicationContext context) {
            if (!context.containsBean("mvcHandlerMappingIntrospector")) {
                throw new NoSuchBeanDefinitionException("mvcHandlerMappingIntrospector", "A Bean named mvcHandlerMappingIntrospector of type " + HandlerMappingIntrospector.class.getName() + " is required to use MvcRequestMatcher. Please ensure Spring Security & Spring MVC are configured in a shared ApplicationContext.");
            } else {
                HandlerMappingIntrospector mappingIntrospector = (HandlerMappingIntrospector)context.getBean("mvcHandlerMappingIntrospector", HandlerMappingIntrospector.class);
                return new CorsFilter(mappingIntrospector);
            }
        }
    }
}
```

```java
package org.springframework.web.filter;

...

public class CorsFilter extends OncePerRequestFilter {
    private final CorsConfigurationSource configSource;
    private CorsProcessor processor = new DefaultCorsProcessor();

    public CorsFilter(CorsConfigurationSource configSource) {
        Assert.notNull(configSource, "CorsConfigurationSource must not be null");
        this.configSource = configSource;
    }

    public void setCorsProcessor(CorsProcessor processor) {
        Assert.notNull(processor, "CorsProcessor must not be null");
        this.processor = processor;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CorsConfiguration corsConfiguration = this.configSource.getCorsConfiguration(request);
        boolean isValid = this.processor.processRequest(corsConfiguration, request, response);
        if (isValid && !CorsUtils.isPreFlightRequest(request)) {
            filterChain.doFilter(request, response);
        }
    }
}
```

📌

- `CORS`의 `Preflight Request`에는 쿠키 (JSESSIONID)가 포함되어 있지 않기 때문에 Spring Security 이전에 처리되어야 합니다.
- `CORS` 가 먼저 처리되도록 하기 위해서 Spring MVC의 `CorsFilter` 를 사용할 수 있으며 `CorsFilter` 에 `CorsConfigurationSource` 를 제공함으로써 Spring Security 와 통합 할 수 있습니다.

---

## 📂 참고할만한 자료

- `CORS Guide`
  - [https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS)
