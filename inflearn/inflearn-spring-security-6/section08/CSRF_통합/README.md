# ☘️ CSRF 통합

---

## 📖 내용
- CSRF 공격을 방지하기 위한 토큰 패턴을 사용하려면 실제 CSRF 토큰을 HTTP 요청 (폼 매개변수, HTTP 헤더) 중 하나에 포함해야 합니다.

![image_1.png](image_1.png)
<sub>※ 이미지 출처: [정수원님의 인프런 강의](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-%EC%99%84%EC%A0%84%EC%A0%95%EB%B3%B5/dashboard)</sub>

---

## 🔍 중심 로직

```java
package org.springframework.security.web.csrf;

...

public final class CsrfFilter extends OncePerRequestFilter {
    public static final RequestMatcher DEFAULT_CSRF_MATCHER = new DefaultRequiresCsrfMatcher();
    private static final String SHOULD_NOT_FILTER = "SHOULD_NOT_FILTER" + CsrfFilter.class.getName();
    private final Log logger = LogFactory.getLog(this.getClass());
    private final CsrfTokenRepository tokenRepository;
    private RequestMatcher requireCsrfProtectionMatcher;
    private AccessDeniedHandler accessDeniedHandler;
    private CsrfTokenRequestHandler requestHandler;

    public CsrfFilter(CsrfTokenRepository tokenRepository) {
        this.requireCsrfProtectionMatcher = DEFAULT_CSRF_MATCHER;
        this.accessDeniedHandler = new AccessDeniedHandlerImpl();
        this.requestHandler = new XorCsrfTokenRequestAttributeHandler();
        Assert.notNull(tokenRepository, "tokenRepository cannot be null");
        this.tokenRepository = tokenRepository;
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Boolean.TRUE.equals(request.getAttribute(SHOULD_NOT_FILTER));
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        DeferredCsrfToken deferredCsrfToken = this.tokenRepository.loadDeferredToken(request, response);
        request.setAttribute(DeferredCsrfToken.class.getName(), deferredCsrfToken);
        CsrfTokenRequestHandler var10000 = this.requestHandler;
        Objects.requireNonNull(deferredCsrfToken);
        var10000.handle(request, response, deferredCsrfToken::get);
        if (!this.requireCsrfProtectionMatcher.matches(request)) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Did not protect against CSRF since request did not match " + String.valueOf(this.requireCsrfProtectionMatcher));
            }

            filterChain.doFilter(request, response);
        } else {
            CsrfToken csrfToken = deferredCsrfToken.get();
            String actualToken = this.requestHandler.resolveCsrfTokenValue(request, csrfToken);
            if (!equalsConstantTime(csrfToken.getToken(), actualToken)) {
                boolean missingToken = deferredCsrfToken.isGenerated();
                this.logger.debug(LogMessage.of(() -> "Invalid CSRF token found for " + UrlUtils.buildFullRequestUrl(request)));
                AccessDeniedException exception = (AccessDeniedException)(!missingToken ? new InvalidCsrfTokenException(csrfToken, actualToken) : new MissingCsrfTokenException(actualToken));
                this.accessDeniedHandler.handle(request, response, exception);
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    public static void skipRequest(HttpServletRequest request) {
        request.setAttribute(SHOULD_NOT_FILTER, Boolean.TRUE);
    }

    public void setRequireCsrfProtectionMatcher(RequestMatcher requireCsrfProtectionMatcher) {
        Assert.notNull(requireCsrfProtectionMatcher, "requireCsrfProtectionMatcher cannot be null");
        this.requireCsrfProtectionMatcher = requireCsrfProtectionMatcher;
    }

    public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
        Assert.notNull(accessDeniedHandler, "accessDeniedHandler cannot be null");
        this.accessDeniedHandler = accessDeniedHandler;
    }

    public void setRequestHandler(CsrfTokenRequestHandler requestHandler) {
        Assert.notNull(requestHandler, "requestHandler cannot be null");
        this.requestHandler = requestHandler;
    }

    private static boolean equalsConstantTime(String expected, String actual) {
        if (expected == actual) {
            return true;
        } else if (expected != null && actual != null) {
            byte[] expectedBytes = Utf8.encode(expected);
            byte[] actualBytes = Utf8.encode(actual);
            return MessageDigest.isEqual(expectedBytes, actualBytes);
        } else {
            return false;
        }
    }

    private static final class DefaultRequiresCsrfMatcher implements RequestMatcher {
        private final HashSet<String> allowedMethods = new HashSet(Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));

        private DefaultRequiresCsrfMatcher() {
        }

        public boolean matches(HttpServletRequest request) {
            return !this.allowedMethods.contains(request.getMethod());
        }

        public String toString() {
            return "CsrfNotRequired " + String.valueOf(this.allowedMethods);
        }
    }
}
```

📌

---

## 📂 참고할만한 자료

