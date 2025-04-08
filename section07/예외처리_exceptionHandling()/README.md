# ☘️ 예외 처리 - exceptionHandling()

---

## 📖 내용
- Spring Security에서 예외 처리는 FilterChain에서 발생한 예외를 처리하는 방법을 제공하며 예외 처리는 인증예외(`AuthenticationException`)와 인가예외(`AccessDeniedException`)로 나눌 수 있습니다.
- 예외 처리는 `ExceptionTranslationFilter`에서 수행되며, 이 필터는 로그인 재시도, 인증 및 인가 예외를 처리하는 데 사용됩니다.
- `HttpSecurity.exceptionHandling()` 메서드를 호출하여 활성화 할 수 있으며 `ExceptionHandlingConfigurer` 클래스에서 설정을 관리합니다.

- `AuthenticationException`
  - `SecurityContext`에서 인증 정보 삭제 - 기존의 `Authentication` 객체가 더 이상 유효하지 않다고 판단하고 `Authentication` 객체를 초기화 합니다.
  - `AuthenticationEntryPoint` 호출
    - `AuthenticationException`이 감지되면 `ExceptionTranslationFilter`는 `AuthenticationEntryPoint`를 호출하여 인증되지 않은 사용자에 대한 인증 실패 처리를 수행합니다.
  - 인증 프로세스의 요청 정보를 저장하고 검색
    - `RequestCache` & `SavedRequest` - 인증 프로세스 동안 전달되는 요청을 세션 혹은 쿠키에 저장
    - 사용자가 인증을 완료한 후 요청을 검색하여 재사용할 수 있으며 기본 구현체는 `HttpSessionRequestCache`입니다.

- `AccessDeniedException`
  - `AccessDeniedHandler` 호출
    - `AccessDeniedException`이 감지되면 `ExceptionTranslationFilter`는 사용자가 익명 사용자인지 여부를 판단하고 익명 사용자인 경우 인증예외처리가 실행되고 익명 사용자가 아닌 경우 `AccessDeniedHandler`를 호출하여 인가 실패 처리를 수행합니다.

---

## 🔍 중심 로직

```java
package org.springframework.security.config.annotation.web.configurers;

...

public final class ExceptionHandlingConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<ExceptionHandlingConfigurer<H>, H> {
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AccessDeniedHandler accessDeniedHandler;
    private LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> defaultEntryPointMappings = new LinkedHashMap();
    private LinkedHashMap<RequestMatcher, AccessDeniedHandler> defaultDeniedHandlerMappings = new LinkedHashMap();

    public ExceptionHandlingConfigurer() {
    }

    public ExceptionHandlingConfigurer<H> accessDeniedPage(String accessDeniedUrl) {
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage(accessDeniedUrl);
        return this.accessDeniedHandler(accessDeniedHandler);
    }

    public ExceptionHandlingConfigurer<H> accessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
        return this;
    }

    public ExceptionHandlingConfigurer<H> defaultAccessDeniedHandlerFor(AccessDeniedHandler deniedHandler, RequestMatcher preferredMatcher) {
        this.defaultDeniedHandlerMappings.put(preferredMatcher, deniedHandler);
        return this;
    }

    public ExceptionHandlingConfigurer<H> authenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        return this;
    }

    public ExceptionHandlingConfigurer<H> defaultAuthenticationEntryPointFor(AuthenticationEntryPoint entryPoint, RequestMatcher preferredMatcher) {
        this.defaultEntryPointMappings.put(preferredMatcher, entryPoint);
        return this;
    }

    AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return this.authenticationEntryPoint;
    }

    AccessDeniedHandler getAccessDeniedHandler() {
        return this.accessDeniedHandler;
    }

    public void configure(H http) {
        AuthenticationEntryPoint entryPoint = this.getAuthenticationEntryPoint(http);
        ExceptionTranslationFilter exceptionTranslationFilter = new ExceptionTranslationFilter(entryPoint, this.getRequestCache(http));
        AccessDeniedHandler deniedHandler = this.getAccessDeniedHandler(http);
        exceptionTranslationFilter.setAccessDeniedHandler(deniedHandler);
        exceptionTranslationFilter.setSecurityContextHolderStrategy(this.getSecurityContextHolderStrategy());
        exceptionTranslationFilter = (ExceptionTranslationFilter)this.postProcess(exceptionTranslationFilter);
        http.addFilter(exceptionTranslationFilter);
    }

    AccessDeniedHandler getAccessDeniedHandler(H http) {
        AccessDeniedHandler deniedHandler = this.accessDeniedHandler;
        if (deniedHandler == null) {
            deniedHandler = this.createDefaultDeniedHandler(http);
        }

        return deniedHandler;
    }

    AuthenticationEntryPoint getAuthenticationEntryPoint(H http) {
        AuthenticationEntryPoint entryPoint = this.authenticationEntryPoint;
        if (entryPoint == null) {
            entryPoint = this.createDefaultEntryPoint(http);
        }

        return entryPoint;
    }

    private AccessDeniedHandler createDefaultDeniedHandler(H http) {
        if (this.defaultDeniedHandlerMappings.isEmpty()) {
            return new AccessDeniedHandlerImpl();
        } else {
            return (AccessDeniedHandler)(this.defaultDeniedHandlerMappings.size() == 1 ? (AccessDeniedHandler)this.defaultDeniedHandlerMappings.values().iterator().next() : new RequestMatcherDelegatingAccessDeniedHandler(this.defaultDeniedHandlerMappings, new AccessDeniedHandlerImpl()));
        }
    }

    private AuthenticationEntryPoint createDefaultEntryPoint(H http) {
        if (this.defaultEntryPointMappings.isEmpty()) {
            return new Http403ForbiddenEntryPoint();
        } else if (this.defaultEntryPointMappings.size() == 1) {
            return (AuthenticationEntryPoint)this.defaultEntryPointMappings.values().iterator().next();
        } else {
            DelegatingAuthenticationEntryPoint entryPoint = new DelegatingAuthenticationEntryPoint(this.defaultEntryPointMappings);
            entryPoint.setDefaultEntryPoint((AuthenticationEntryPoint)this.defaultEntryPointMappings.values().iterator().next());
            return entryPoint;
        }
    }

    private RequestCache getRequestCache(H http) {
        RequestCache result = (RequestCache)http.getSharedObject(RequestCache.class);
        return (RequestCache)(result != null ? result : new HttpSessionRequestCache());
    }
}
```

```java
package org.springframework.security.web.access;

...

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    protected static final Log logger = LogFactory.getLog(AccessDeniedHandlerImpl.class);
    private String errorPage;

    public AccessDeniedHandlerImpl() {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (response.isCommitted()) {
            logger.trace("Did not write to response since already committed");
        } else if (this.errorPage == null) {
            logger.debug("Responding with 403 status code");
            response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
        } else {
            request.setAttribute("SPRING_SECURITY_403_EXCEPTION", accessDeniedException);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            if (logger.isDebugEnabled()) {
                logger.debug(LogMessage.format("Forwarding to %s with status code 403", this.errorPage));
            }

            request.getRequestDispatcher(this.errorPage).forward(request, response);
        }
    }

    public void setErrorPage(String errorPage) {
        Assert.isTrue(errorPage == null || errorPage.startsWith("/"), "errorPage must begin with '/'");
        this.errorPage = errorPage;
    }
}
```

```java
package org.springframework.security.web.access;

...

public class ExceptionTranslationFilter extends GenericFilterBean implements MessageSourceAware {
    private SecurityContextHolderStrategy securityContextHolderStrategy;
    private AccessDeniedHandler accessDeniedHandler;
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationTrustResolver authenticationTrustResolver;
    private ThrowableAnalyzer throwableAnalyzer;
    private final RequestCache requestCache;
    protected MessageSourceAccessor messages;

    public ExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
        this(authenticationEntryPoint, new HttpSessionRequestCache());
    }

    public ExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint, RequestCache requestCache) {
        this.securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        this.accessDeniedHandler = new AccessDeniedHandlerImpl();
        this.authenticationTrustResolver = new AuthenticationTrustResolverImpl();
        this.throwableAnalyzer = new DefaultThrowableAnalyzer();
        this.messages = SpringSecurityMessageSource.getAccessor();
        Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint cannot be null");
        Assert.notNull(requestCache, "requestCache cannot be null");
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.requestCache = requestCache;
    }

    public void afterPropertiesSet() {
        Assert.notNull(this.authenticationEntryPoint, "authenticationEntryPoint must be specified");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (IOException ex) {
            throw ex;
        } catch (Exception var8) {
            Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(var8);
            RuntimeException securityException = (AuthenticationException)this.throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
            if (securityException == null) {
                securityException = (AccessDeniedException)this.throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
            }

            if (securityException == null) {
                this.rethrow(var8);
            }

            if (response.isCommitted()) {
                throw new ServletException("Unable to handle the Spring Security Exception because the response is already committed.", var8);
            }

            this.handleSpringSecurityException(request, response, chain, securityException);
        }

    }

    private void rethrow(Exception ex) throws ServletException {
        if (ex instanceof ServletException) {
            throw (ServletException)ex;
        } else if (ex instanceof RuntimeException) {
            throw (RuntimeException)ex;
        } else {
            throw new RuntimeException(ex);
        }
    }

    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return this.authenticationEntryPoint;
    }

    protected AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return this.authenticationTrustResolver;
    }

    private void handleSpringSecurityException(HttpServletRequest request, HttpServletResponse response, FilterChain chain, RuntimeException exception) throws IOException, ServletException {
        if (exception instanceof AuthenticationException) {
            this.handleAuthenticationException(request, response, chain, (AuthenticationException)exception);
        } else if (exception instanceof AccessDeniedException) {
            this.handleAccessDeniedException(request, response, chain, (AccessDeniedException)exception);
        }

    }

    private void handleAuthenticationException(HttpServletRequest request, HttpServletResponse response, FilterChain chain, AuthenticationException exception) throws ServletException, IOException {
        this.logger.trace("Sending to authentication entry point since authentication failed", exception);
        this.sendStartAuthentication(request, response, chain, exception);
    }

    private void handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response, FilterChain chain, AccessDeniedException exception) throws ServletException, IOException {
        Authentication authentication = this.securityContextHolderStrategy.getContext().getAuthentication();
        boolean isAnonymous = this.authenticationTrustResolver.isAnonymous(authentication);
        if (!isAnonymous && !this.authenticationTrustResolver.isRememberMe(authentication)) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.format("Sending %s to access denied handler since access is denied", authentication), exception);
            }

            this.accessDeniedHandler.handle(request, response, exception);
        } else {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.format("Sending %s to authentication entry point since access is denied", authentication), exception);
            }

            this.sendStartAuthentication(request, response, chain, new InsufficientAuthenticationException(this.messages.getMessage("ExceptionTranslationFilter.insufficientAuthentication", "Full authentication is required to access this resource")));
        }

    }

    protected void sendStartAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, AuthenticationException reason) throws ServletException, IOException {
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        this.securityContextHolderStrategy.setContext(context);
        this.requestCache.saveRequest(request, response);
        this.authenticationEntryPoint.commence(request, response, reason);
    }

    public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
        Assert.notNull(accessDeniedHandler, "AccessDeniedHandler required");
        this.accessDeniedHandler = accessDeniedHandler;
    }

    public void setAuthenticationTrustResolver(AuthenticationTrustResolver authenticationTrustResolver) {
        Assert.notNull(authenticationTrustResolver, "authenticationTrustResolver must not be null");
        this.authenticationTrustResolver = authenticationTrustResolver;
    }

    public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
        Assert.notNull(throwableAnalyzer, "throwableAnalyzer must not be null");
        this.throwableAnalyzer = throwableAnalyzer;
    }

    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        Assert.notNull(securityContextHolderStrategy, "securityContextHolderStrategy cannot be null");
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
        private DefaultThrowableAnalyzer() {
        }

        protected void initExtractorMap() {
            super.initExtractorMap();
            this.registerExtractor(ServletException.class, (throwable) -> {
                ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
                return ((ServletException)throwable).getRootCause();
            });
        }
    }
}
```

📌
- `accessDeniedPage()` - 접근 거부 페이지 설정
- `accessDeniedHandler()` - 접근 거부 핸들러 설정
- `defaultAccessDeniedHandlerFor()` - 기본 접근 거부 핸들러 설정
- `authenticationEntryPoint()` - `AuthenticationEntryPoint` 설정
- `defaultAuthenticationEntryPointFor()` - 기본 `AuthenticationEntryPoint` 설정

- `AccessDeniedHandler`는 기본적으로 `AccessDeniedHandlerImpl` 클래스가 사용됩니다.
- `AuthenticationEntryPoint`는 인증 프로세스마다 기본적으로 제공되는 클래스들이 설정됩니다.
  - `UsernamePasswordAuthenticationFilter`: `LoginUrlAuthenticationEntryPoint`
  - `BasicAuthenticationFilter`: `LoginUrlAuthenticationEntryPoint`
  - 아무런 인증 프롷세스가 설정되지 않으면 `Http403ForbiddenEntryPoint`가 설정됩니다.
  - 사용자 정의 `AuthenticationEntryPoint` 구현이 가장 우선적으로 수행되며 이 때 기본 로그인 페이지 생성이 무시됩니다.

---

## 📂 참고할만한 자료
