# ☘️ Servlet API 통합 - SecurityContextHolderAwareRequestFilter

---

## 📖 내용
- Spring Security는 Servlet 3와 Spring MVC와 통합을 통해 여러 편리한 기능들을 사용할 수 있습니다.
- 인증 관련 기능들을 피렅가 아닌 서블릿 영역에서 처리할 수 있습니다.

- Servlet 3+ 통합
  - `SecurityContextHolderAwareRequestFilter`
    - HTTP 요청이 처리될 때 `HttpServletRequest`에 보안 관련 메소드를 추가적으로 제공하는 `SecurityContextHolderAwareRequestWrapper` 클래스를 적용합니다.
    - 이를 통해 서블릿 API에서 인증 관련 작업들을 처리할 수 있습니다.
  - `HttpServlet3RequestFactory`
    - Servlet 3 API와의 통합을 제공하기 위한 `Servlet3SecurityContextHolderAwareRequestWrapper` 객체를 생성합니다.
  - `Servlet3SecurityContextHolderAwareRequestWrapper`
    - `HttpServletRequest` 의 래퍼 클래스로서 Servlet 3의 기능을 지원하면서 동시에 `SecurityContextHolder` 와의 통합을 제공합니다.
    - 이 래퍼를 사용함으로써 `SecurityContext` 에 쉽게 접근할 수 있고 Servlet 3의 비동기 처리와 같은 기능을 사용하는 동안 보안 컨텍스트를 올바르게 관리할 수 있습니다.

---

## 🔍 중심 로직

```java
package org.springframework.security.web.servletapi;

...

public class SecurityContextHolderAwareRequestFilter extends GenericFilterBean {
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private String rolePrefix = "ROLE_";
    private HttpServletRequestFactory requestFactory;
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationManager authenticationManager;
    private List<LogoutHandler> logoutHandlers;
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public SecurityContextHolderAwareRequestFilter() {
    }

    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        Assert.notNull(securityContextRepository, "securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }

    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        Assert.notNull(securityContextHolderStrategy, "securityContextHolderStrategy cannot be null");
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    public void setRolePrefix(String rolePrefix) {
        Assert.notNull(rolePrefix, "Role prefix must not be null");
        this.rolePrefix = rolePrefix;
        this.updateFactory();
    }

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setLogoutHandlers(List<LogoutHandler> logoutHandlers) {
        this.logoutHandlers = logoutHandlers;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(this.requestFactory.create((HttpServletRequest)req, (HttpServletResponse)res), res);
    }

    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        this.updateFactory();
    }

    private void updateFactory() {
        String rolePrefix = this.rolePrefix;
        this.requestFactory = this.createServlet3Factory(rolePrefix);
    }

    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        Assert.notNull(trustResolver, "trustResolver cannot be null");
        this.trustResolver = trustResolver;
        this.updateFactory();
    }

    private HttpServletRequestFactory createServlet3Factory(String rolePrefix) {
        HttpServlet3RequestFactory factory = new HttpServlet3RequestFactory(rolePrefix, this.securityContextRepository);
        factory.setTrustResolver(this.trustResolver);
        factory.setAuthenticationEntryPoint(this.authenticationEntryPoint);
        factory.setAuthenticationManager(this.authenticationManager);
        factory.setLogoutHandlers(this.logoutHandlers);
        factory.setSecurityContextHolderStrategy(this.securityContextHolderStrategy);
        return factory;
    }
}
```

```java
package org.springframework.security.web.servletapi;

...

final class HttpServlet3RequestFactory implements HttpServletRequestFactory {
    private Log logger = LogFactory.getLog(this.getClass());
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final String rolePrefix;
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationManager authenticationManager;
    private List<LogoutHandler> logoutHandlers;
    private SecurityContextRepository securityContextRepository;

    HttpServlet3RequestFactory(String rolePrefix, SecurityContextRepository securityContextRepository) {
        this.rolePrefix = rolePrefix;
        this.securityContextRepository = securityContextRepository;
    }

    void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    void setLogoutHandlers(List<LogoutHandler> logoutHandlers) {
        this.logoutHandlers = logoutHandlers;
    }

    void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        Assert.notNull(trustResolver, "trustResolver cannot be null");
        this.trustResolver = trustResolver;
    }

    void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        Assert.notNull(securityContextHolderStrategy, "securityContextHolderStrategy cannot be null");
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    public HttpServletRequest create(HttpServletRequest request, HttpServletResponse response) {
        Servlet3SecurityContextHolderAwareRequestWrapper wrapper = new Servlet3SecurityContextHolderAwareRequestWrapper(request, this.rolePrefix, response);
        wrapper.setSecurityContextHolderStrategy(this.securityContextHolderStrategy);
        return wrapper;
    }

    private class Servlet3SecurityContextHolderAwareRequestWrapper extends SecurityContextHolderAwareRequestWrapper {
        private final HttpServletResponse response;

        Servlet3SecurityContextHolderAwareRequestWrapper(HttpServletRequest request, String rolePrefix, HttpServletResponse response) {
            super(request, HttpServlet3RequestFactory.this.trustResolver, rolePrefix);
            this.response = response;
        }

        public AsyncContext getAsyncContext() {
            AsyncContext asyncContext = super.getAsyncContext();
            return asyncContext == null ? null : new SecurityContextAsyncContext(asyncContext);
        }

        public AsyncContext startAsync() {
            AsyncContext startAsync = super.startAsync();
            return new SecurityContextAsyncContext(startAsync);
        }

        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
            AsyncContext startAsync = super.startAsync(servletRequest, servletResponse);
            return new SecurityContextAsyncContext(startAsync);
        }

        public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
            AuthenticationEntryPoint entryPoint = HttpServlet3RequestFactory.this.authenticationEntryPoint;
            if (entryPoint == null) {
                HttpServlet3RequestFactory.this.logger.debug("authenticationEntryPoint is null, so allowing original HttpServletRequest to handle authenticate");
                return super.authenticate(response);
            } else if (this.isAuthenticated()) {
                return true;
            } else {
                entryPoint.commence(this, response, new AuthenticationCredentialsNotFoundException("User is not Authenticated"));
                return false;
            }
        }

        public void login(String username, String password) throws ServletException {
            if (this.isAuthenticated()) {
                throw new ServletException("Cannot perform login for '" + username + "' already authenticated as '" + this.getRemoteUser() + "'");
            } else {
                AuthenticationManager authManager = HttpServlet3RequestFactory.this.authenticationManager;
                if (authManager == null) {
                    HttpServlet3RequestFactory.this.logger.debug("authenticationManager is null, so allowing original HttpServletRequest to handle login");
                    super.login(username, password);
                } else {
                    Authentication authentication = this.getAuthentication(authManager, username, password);
                    SecurityContext context = HttpServlet3RequestFactory.this.securityContextHolderStrategy.createEmptyContext();
                    context.setAuthentication(authentication);
                    HttpServlet3RequestFactory.this.securityContextHolderStrategy.setContext(context);
                    HttpServlet3RequestFactory.this.securityContextRepository.saveContext(context, this, this.response);
                }
            }
        }

        private Authentication getAuthentication(AuthenticationManager authManager, String username, String password) throws ServletException {
            try {
                UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
                Object details = HttpServlet3RequestFactory.this.authenticationDetailsSource.buildDetails(this);
                authentication.setDetails(details);
                return authManager.authenticate(authentication);
            } catch (AuthenticationException ex) {
                HttpServlet3RequestFactory.this.securityContextHolderStrategy.clearContext();
                throw new ServletException(ex.getMessage(), ex);
            }
        }

        public void logout() throws ServletException {
            List<LogoutHandler> handlers = HttpServlet3RequestFactory.this.logoutHandlers;
            if (CollectionUtils.isEmpty(handlers)) {
                HttpServlet3RequestFactory.this.logger.debug("logoutHandlers is null, so allowing original HttpServletRequest to handle logout");
                super.logout();
            } else {
                Authentication authentication = HttpServlet3RequestFactory.this.securityContextHolderStrategy.getContext().getAuthentication();

                for(LogoutHandler handler : handlers) {
                    handler.logout(this, this.response, authentication);
                }

            }
        }

        private boolean isAuthenticated() {
            return this.getUserPrincipal() != null;
        }
    }

    ... other methods
}
```

📌

---

## 📂 참고할만한 자료

