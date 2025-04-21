# ☘️ 비동기 인증

---

## 📖 내용


---

## 🔍 중심 로직

```java
package org.springframework.security.web.authentication;

...

public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean implements ApplicationEventPublisherAware, MessageSourceAware {
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    protected ApplicationEventPublisher eventPublisher;
    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private AuthenticationManager authenticationManager;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private RememberMeServices rememberMeServices = new NullRememberMeServices();
    private RequestMatcher requiresAuthenticationRequestMatcher;
    private boolean continueChainBeforeSuccessfulAuthentication = false;
    private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();
    private boolean allowSessionCreation = true;
    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
    
    // 기본 repository로 RequestAttributeSecurityContextRepository 사용
    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    ...
}
```

```java
package org.springframework.security.web.context;

...

public final class RequestAttributeSecurityContextRepository implements SecurityContextRepository {
    public static final String DEFAULT_REQUEST_ATTR_NAME = RequestAttributeSecurityContextRepository.class.getName().concat(".SPRING_SECURITY_CONTEXT");
    private final String requestAttributeName;
    private SecurityContextHolderStrategy securityContextHolderStrategy;

  ...

    public boolean containsContext(HttpServletRequest request) {
        return this.getContext(request) != null;
    }

  ...

    private SecurityContext getContext(HttpServletRequest request) {
        return (SecurityContext)request.getAttribute(this.requestAttributeName);
    }

    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(this.requestAttributeName, context);
    }

  ...
}
```

```java
package org.springframework.security.web.context;

...

public class HttpSessionSecurityContextRepository implements SecurityContextRepository {
    public static final String SPRING_SECURITY_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";
    protected final Log logger = LogFactory.getLog(this.getClass());
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private Object contextObject;
    private boolean allowSessionCreation;
    private boolean disableUrlRewriting;
    private String springSecurityContextKey;
    private AuthenticationTrustResolver trustResolver;

    public HttpSessionSecurityContextRepository() {
        this.contextObject = this.securityContextHolderStrategy.createEmptyContext();
        this.allowSessionCreation = true;
        this.disableUrlRewriting = false;
        this.springSecurityContextKey = "SPRING_SECURITY_CONTEXT";
        this.trustResolver = new AuthenticationTrustResolverImpl();
    }

    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        HttpServletResponse response = requestResponseHolder.getResponse();
        HttpSession httpSession = request.getSession(false);
        SecurityContext context = this.readSecurityContextFromSession(httpSession);
        if (context == null) {
            context = this.generateNewContext();
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.format("Created %s", context));
            }
        }

        if (response != null) {
            SaveToSessionResponseWrapper wrappedResponse = new SaveToSessionResponseWrapper(response, request, httpSession != null, context);
            wrappedResponse.setSecurityContextHolderStrategy(this.securityContextHolderStrategy);
            requestResponseHolder.setResponse(wrappedResponse);
            requestResponseHolder.setRequest(new SaveToSessionRequestWrapper(request, wrappedResponse));
        }

        return context;
    }

    public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
        Supplier<SecurityContext> supplier = () -> this.readSecurityContextFromSession(request.getSession(false));
        return new SupplierDeferredSecurityContext(supplier, this.securityContextHolderStrategy);
    }

    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        SaveContextOnUpdateOrErrorResponseWrapper responseWrapper = (SaveContextOnUpdateOrErrorResponseWrapper)WebUtils.getNativeResponse(response, SaveContextOnUpdateOrErrorResponseWrapper.class);
        if (responseWrapper == null) {
            this.saveContextInHttpSession(context, request);
        } else {
            responseWrapper.saveContext(context);
        }
    }

    private void saveContextInHttpSession(SecurityContext context, HttpServletRequest request) {
        if (!this.isTransient(context) && !this.isTransient(context.getAuthentication())) {
            SecurityContext emptyContext = this.generateNewContext();
            if (emptyContext.equals(context)) {
                HttpSession session = request.getSession(false);
                this.removeContextFromSession(context, session);
            } else {
                boolean createSession = this.allowSessionCreation;
                HttpSession session = request.getSession(createSession);
                this.setContextInSession(context, session);
            }

        }
    }

    private void setContextInSession(SecurityContext context, HttpSession session) {
        if (session != null) {
            session.setAttribute(this.springSecurityContextKey, context);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(LogMessage.format("Stored %s to HttpSession [%s]", context, session));
            }
        }

    }

    private void removeContextFromSession(SecurityContext context, HttpSession session) {
        if (session != null) {
            session.removeAttribute(this.springSecurityContextKey);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(LogMessage.format("Removed %s from HttpSession [%s]", context, session));
            }
        }

    }

    public boolean containsContext(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        } else {
            return session.getAttribute(this.springSecurityContextKey) != null;
        }
    }

    private SecurityContext readSecurityContextFromSession(HttpSession httpSession) {
        if (httpSession == null) {
            this.logger.trace("No HttpSession currently exists");
            return null;
        } else {
            Object contextFromSession = httpSession.getAttribute(this.springSecurityContextKey);
            if (contextFromSession == null) {
                if (this.logger.isTraceEnabled()) {
                    this.logger.trace(LogMessage.format("Did not find SecurityContext in HttpSession %s using the SPRING_SECURITY_CONTEXT session attribute", httpSession.getId()));
                }

                return null;
            } else if (!(contextFromSession instanceof SecurityContext)) {
                this.logger.warn(LogMessage.format("%s did not contain a SecurityContext but contained: '%s'; are you improperly modifying the HttpSession directly (you should always use SecurityContextHolder) or using the HttpSession attribute reserved for this class?", this.springSecurityContextKey, contextFromSession));
                return null;
            } else {
                if (this.logger.isTraceEnabled()) {
                    this.logger.trace(LogMessage.format("Retrieved %s from %s", contextFromSession, this.springSecurityContextKey));
                } else if (this.logger.isDebugEnabled()) {
                    this.logger.debug(LogMessage.format("Retrieved %s", contextFromSession));
                }

                return (SecurityContext)contextFromSession;
            }
        }
    }
    
  ...

    final class SaveToSessionResponseWrapper extends SaveContextOnUpdateOrErrorResponseWrapper {
        private final Log logger;
        private final HttpServletRequest request;
        private final boolean httpSessionExistedAtStartOfRequest;
        private final SecurityContext contextBeforeExecution;
        private final Authentication authBeforeExecution;
        private boolean isSaveContextInvoked;

        SaveToSessionResponseWrapper(HttpServletResponse response, HttpServletRequest request, boolean httpSessionExistedAtStartOfRequest, SecurityContext context) {
            super(response, HttpSessionSecurityContextRepository.this.disableUrlRewriting);
            this.logger = HttpSessionSecurityContextRepository.this.logger;
            this.request = request;
            this.httpSessionExistedAtStartOfRequest = httpSessionExistedAtStartOfRequest;
            this.contextBeforeExecution = context;
            this.authBeforeExecution = context.getAuthentication();
        }

        protected void saveContext(SecurityContext context) {
            if (!HttpSessionSecurityContextRepository.this.isTransient(context)) {
                Authentication authentication = context.getAuthentication();
                if (!HttpSessionSecurityContextRepository.this.isTransient(authentication)) {
                    HttpSession httpSession = this.request.getSession(false);
                    String springSecurityContextKey = HttpSessionSecurityContextRepository.this.springSecurityContextKey;
                    if (authentication != null && !HttpSessionSecurityContextRepository.this.trustResolver.isAnonymous(authentication)) {
                        httpSession = httpSession != null ? httpSession : this.createNewSessionIfAllowed(context);
                        if (httpSession != null && (this.contextChanged(context) || httpSession.getAttribute(springSecurityContextKey) == null)) {
                            HttpSessionSecurityContextRepository.this.saveContextInHttpSession(context, this.request);
                            this.isSaveContextInvoked = true;
                        }

                    } else {
                        if (httpSession != null && this.authBeforeExecution != null) {
                            httpSession.removeAttribute(springSecurityContextKey);
                            this.isSaveContextInvoked = true;
                        }

                        if (this.logger.isDebugEnabled()) {
                            if (authentication == null) {
                                this.logger.debug("Did not store empty SecurityContext");
                            } else {
                                this.logger.debug("Did not store anonymous SecurityContext");
                            }
                        }

                    }
                }
            }
        }

        private boolean contextChanged(SecurityContext context) {
            return this.isSaveContextInvoked || context != this.contextBeforeExecution || context.getAuthentication() != this.authBeforeExecution;
        }

        private HttpSession createNewSessionIfAllowed(SecurityContext context) {
            if (this.httpSessionExistedAtStartOfRequest) {
                this.logger.debug("HttpSession is now null, but was not null at start of request; session was invalidated, so do not create a new session");
                return null;
            } else if (!HttpSessionSecurityContextRepository.this.allowSessionCreation) {
                this.logger.debug("The HttpSession is currently null, and the " + HttpSessionSecurityContextRepository.class.getSimpleName() + " is prohibited from creating an HttpSession (because the allowSessionCreation property is false) - SecurityContext thus not stored for next request");
                return null;
            } else if (HttpSessionSecurityContextRepository.this.contextObject.equals(context)) {
                this.logger.debug(LogMessage.format("HttpSession is null, but SecurityContext has not changed from default empty context %s so not creating HttpSession or storing SecurityContext", context));
                return null;
            } else {
                try {
                    HttpSession session = this.request.getSession(true);
                    this.logger.debug("Created HttpSession as SecurityContext is non-default");
                    return session;
                } catch (IllegalStateException var3) {
                    this.logger.warn("Failed to create a session, as response has been committed. Unable to store SecurityContext.");
                    return null;
                }
            }
        }
    }
}
```

📌
- Rest API 인증 기능 구현을 위해 `AbstractAuthenticationProcessingFilter`를 상속받은 RestFilter를 구현하여 Rest API 기반 인증을 처리합니다.
- 이 때 `AbstractAuthenticationProcessingFilter`의 `authenticationDetailsSource`를 상속하면 `RequestAttributeSecurityContextRepository` 가 기본으로 설정 되는데, 인증 정보를 request에 저장합니다.
- 학습 예제의 경우 세션에 인증 정보를 저장해야 하기 때문에 `HttpSessionSecurityContextRepository`를 지정하여 사용합니다.

---

## 📂 참고할만한 자료

