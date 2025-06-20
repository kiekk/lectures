# ☘️ 로그 아웃 - logout() -1 ~ 2

---

## 📖 내용
- 로그아웃은 기본적으로 `DefaultLogoutPageGeneratingFilter`를 통해 로그아웃 페이지를 제공하며 GET "/logout" URl로 접근이 가능합니다.
- 로그아웃 실행은 POST "/logout"으로만 가능하지만 CSRF를 비활성화 할 경우 또는 RequestMatcher를 사용할 경우 GET, POST, PUT, DELETE 모두 가능합니다.
- 로그아웃 페이지/기능도 커스텀 할 수 으며 페이지를 커스텀할 경우 기능도 같이 커스텀해야 합니다.

- `logout()` 메서드를 사용하여 활성화할 수 있으며 `LogoutConfigurer` 를 통해 설정할 수 있습니다.
---

## 🔍 중심 로직

```java
package org.springframework.security.web.authentication.logout;

...

public class LogoutFilter extends GenericFilterBean {
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private RequestMatcher logoutRequestMatcher;
    private final LogoutHandler handler;
    private final LogoutSuccessHandler logoutSuccessHandler;

    public LogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers) {
        this.handler = new CompositeLogoutHandler(handlers);
        Assert.notNull(logoutSuccessHandler, "logoutSuccessHandler cannot be null");
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.setFilterProcessesUrl("/logout");
    }

    public LogoutFilter(String logoutSuccessUrl, LogoutHandler... handlers) {
        this.handler = new CompositeLogoutHandler(handlers);
        Assert.isTrue(!StringUtils.hasLength(logoutSuccessUrl) || UrlUtils.isValidRedirectUrl(logoutSuccessUrl), () -> logoutSuccessUrl + " isn't a valid redirect URL");
        SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        if (StringUtils.hasText(logoutSuccessUrl)) {
            urlLogoutSuccessHandler.setDefaultTargetUrl(logoutSuccessUrl);
        }

        this.logoutSuccessHandler = urlLogoutSuccessHandler;
        this.setFilterProcessesUrl("/logout");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (this.requiresLogout(request, response)) {
            Authentication auth = this.securityContextHolderStrategy.getContext().getAuthentication();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(LogMessage.format("Logging out [%s]", auth));
            }

            this.handler.logout(request, response, auth);
            this.logoutSuccessHandler.onLogoutSuccess(request, response, auth);
        } else {
            chain.doFilter(request, response);
        }
    }

    protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
        if (this.logoutRequestMatcher.matches(request)) {
            return true;
        } else {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.format("Did not match request to %s", this.logoutRequestMatcher));
            }

            return false;
        }
    }

    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        Assert.notNull(securityContextHolderStrategy, "securityContextHolderStrategy cannot be null");
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    public void setLogoutRequestMatcher(RequestMatcher logoutRequestMatcher) {
        Assert.notNull(logoutRequestMatcher, "logoutRequestMatcher cannot be null");
        this.logoutRequestMatcher = logoutRequestMatcher;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.logoutRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
    }
}
```

```java
package org.springframework.security.config.annotation.web.configurers;

...

public final class LogoutConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<LogoutConfigurer<H>, H> {
    private List<LogoutHandler> logoutHandlers = new ArrayList();
    private SecurityContextLogoutHandler contextLogoutHandler = new SecurityContextLogoutHandler();
    private String logoutSuccessUrl = "/login?logout";
    private LogoutSuccessHandler logoutSuccessHandler;
    private String logoutUrl = "/logout";
    private RequestMatcher logoutRequestMatcher;
    private boolean permitAll;
    private boolean customLogoutSuccess;
    private LinkedHashMap<RequestMatcher, LogoutSuccessHandler> defaultLogoutSuccessHandlerMappings = new LinkedHashMap();

    public LogoutConfigurer() {
    }

    public LogoutConfigurer<H> addLogoutHandler(LogoutHandler logoutHandler) {
        Assert.notNull(logoutHandler, "logoutHandler cannot be null");
        this.logoutHandlers.add(logoutHandler);
        return this;
    }

    public LogoutConfigurer<H> clearAuthentication(boolean clearAuthentication) {
        this.contextLogoutHandler.setClearAuthentication(clearAuthentication);
        return this;
    }

    public LogoutConfigurer<H> invalidateHttpSession(boolean invalidateHttpSession) {
        this.contextLogoutHandler.setInvalidateHttpSession(invalidateHttpSession);
        return this;
    }

    public LogoutConfigurer<H> logoutUrl(String logoutUrl) {
        this.logoutRequestMatcher = null;
        this.logoutUrl = logoutUrl;
        return this;
    }

    public LogoutConfigurer<H> logoutRequestMatcher(RequestMatcher logoutRequestMatcher) {
        this.logoutRequestMatcher = logoutRequestMatcher;
        return this;
    }

    public LogoutConfigurer<H> logoutSuccessUrl(String logoutSuccessUrl) {
        this.customLogoutSuccess = true;
        this.logoutSuccessUrl = logoutSuccessUrl;
        return this;
    }

    public LogoutConfigurer<H> permitAll() {
        return this.permitAll(true);
    }

    public LogoutConfigurer<H> deleteCookies(String... cookieNamesToClear) {
        return this.addLogoutHandler(new CookieClearingLogoutHandler(cookieNamesToClear));
    }

    public LogoutConfigurer<H> logoutSuccessHandler(LogoutSuccessHandler logoutSuccessHandler) {
        this.logoutSuccessUrl = null;
        this.customLogoutSuccess = true;
        this.logoutSuccessHandler = logoutSuccessHandler;
        return this;
    }

    public LogoutConfigurer<H> defaultLogoutSuccessHandlerFor(LogoutSuccessHandler handler, RequestMatcher preferredMatcher) {
        Assert.notNull(handler, "handler cannot be null");
        Assert.notNull(preferredMatcher, "preferredMatcher cannot be null");
        this.defaultLogoutSuccessHandlerMappings.put(preferredMatcher, handler);
        return this;
    }

    public LogoutConfigurer<H> permitAll(boolean permitAll) {
        this.permitAll = permitAll;
        return this;
    }

    public LogoutSuccessHandler getLogoutSuccessHandler() {
        LogoutSuccessHandler handler = this.logoutSuccessHandler;
        if (handler == null) {
            handler = this.createDefaultSuccessHandler();
            this.logoutSuccessHandler = handler;
        }

        return handler;
    }

    private LogoutSuccessHandler createDefaultSuccessHandler() {
        SimpleUrlLogoutSuccessHandler urlLogoutHandler = new SimpleUrlLogoutSuccessHandler();
        urlLogoutHandler.setDefaultTargetUrl(this.logoutSuccessUrl);
        if (this.defaultLogoutSuccessHandlerMappings.isEmpty()) {
            return urlLogoutHandler;
        } else {
            DelegatingLogoutSuccessHandler successHandler = new DelegatingLogoutSuccessHandler(this.defaultLogoutSuccessHandlerMappings);
            successHandler.setDefaultLogoutSuccessHandler(urlLogoutHandler);
            return successHandler;
        }
    }

    public void init(H http) {
        if (this.permitAll) {
            PermitAllSupport.permitAll(http, new String[]{this.logoutSuccessUrl});
            PermitAllSupport.permitAll(http, new RequestMatcher[]{this.getLogoutRequestMatcher(http)});
        }

        DefaultLoginPageGeneratingFilter loginPageGeneratingFilter = (DefaultLoginPageGeneratingFilter)http.getSharedObject(DefaultLoginPageGeneratingFilter.class);
        if (loginPageGeneratingFilter != null && !this.isCustomLogoutSuccess()) {
            loginPageGeneratingFilter.setLogoutSuccessUrl(this.getLogoutSuccessUrl());
        }

    }

    public void configure(H http) throws Exception {
        LogoutFilter logoutFilter = this.createLogoutFilter(http);
        http.addFilter(logoutFilter);
    }

    boolean isCustomLogoutSuccess() {
        return this.customLogoutSuccess;
    }

    private String getLogoutSuccessUrl() {
        return this.logoutSuccessUrl;
    }

    public List<LogoutHandler> getLogoutHandlers() {
        return this.logoutHandlers;
    }

    private LogoutFilter createLogoutFilter(H http) {
        this.contextLogoutHandler.setSecurityContextHolderStrategy(this.getSecurityContextHolderStrategy());
        this.contextLogoutHandler.setSecurityContextRepository(this.getSecurityContextRepository(http));
        this.logoutHandlers.add(this.contextLogoutHandler);
        this.logoutHandlers.add((LogoutHandler)this.postProcess(new LogoutSuccessEventPublishingLogoutHandler()));
        LogoutHandler[] handlers = (LogoutHandler[])this.logoutHandlers.toArray(new LogoutHandler[0]);
        LogoutFilter result = new LogoutFilter(this.getLogoutSuccessHandler(), handlers);
        result.setSecurityContextHolderStrategy(this.getSecurityContextHolderStrategy());
        result.setLogoutRequestMatcher(this.getLogoutRequestMatcher(http));
        result = (LogoutFilter)this.postProcess(result);
        return result;
    }

    private SecurityContextRepository getSecurityContextRepository(H http) {
        SecurityContextRepository securityContextRepository = (SecurityContextRepository)http.getSharedObject(SecurityContextRepository.class);
        if (securityContextRepository == null) {
            securityContextRepository = new HttpSessionSecurityContextRepository();
        }

        return securityContextRepository;
    }

    private RequestMatcher getLogoutRequestMatcher(H http) {
        if (this.logoutRequestMatcher != null) {
            return this.logoutRequestMatcher;
        } else {
            this.logoutRequestMatcher = this.createLogoutRequestMatcher(http);
            return this.logoutRequestMatcher;
        }
    }

    private RequestMatcher createLogoutRequestMatcher(H http) {
        RequestMatcher post = this.createLogoutRequestMatcher("POST");
        if (http.getConfigurer(CsrfConfigurer.class) != null) {
            return post;
        } else {
            RequestMatcher get = this.createLogoutRequestMatcher("GET");
            RequestMatcher put = this.createLogoutRequestMatcher("PUT");
            RequestMatcher delete = this.createLogoutRequestMatcher("DELETE");
            return new OrRequestMatcher(new RequestMatcher[]{get, post, put, delete});
        }
    }

    private RequestMatcher createLogoutRequestMatcher(String httpMethod) {
        return new AntPathRequestMatcher(this.logoutUrl, httpMethod);
    }
}
```

```java
import java.beans.Customizer;

@Configuration
public class SecurityConfig {

    // 1. 커스텀 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .logout(logout ->
                        logout
                                .logoutUrl()
                                .logoutRequestMatcher()
                                .logoutSuccessUrl()
                                .logoutSuccessHandler()
                                .clearAuthentication()
                                .invalidateHttpSession()
                                .deleteCookies()
                                .addLogoutHandler()
                                .permitAll()
                )
                .build();
    }

    // 2. 기본 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .logout(Customizer.withDefaults())
                .build();
    }
}
```

📌  요약

- `LogoutConfigurer`
  - `logoutUrl()` : 로그아웃 URL 설정 (default: logout)
  - `logoutRequestMatcher()` : 로그아웃 URL 설정(AntPathRequestMatcher를 사용하여 지정)
  - `logoutSuccessUrl()` : 로그아웃 성공 시 리다이렉트 할 URL 설정 (default: /login?logout)
  - `logoutSuccessHandler()` : 로그아웃 성공 시 리다이렉트 할 핸들러 설정
  - `permitAll()` : logoutUrl(), RequestMatcher() 의 URL에 대한 모든 사용자의 접근을 허용 (default: false)
  - `addLogoutHandler()` : 로그아웃 핸들러 추가 (기존의 로그아웃 핸들러 뒤에 추가됨)
  - `deleteCookies()` : 로그아웃 시 삭제할 쿠키 이름 설정
  - `invalidateHttpSession()` : 로그아웃 시 세션 무효화 여부 설정 (default: true)
  - `clearAuthentication()` : 로그아웃 시 인증 정보 삭제(SecurityContextLogoutHandler가 Authentication 객체를 삭제) 여부 설정 (default: true)

---
