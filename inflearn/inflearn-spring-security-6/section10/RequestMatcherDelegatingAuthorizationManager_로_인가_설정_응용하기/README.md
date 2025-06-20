# ☘️ RequestMatcherDelegatingAuthorizationManager 로 인가 설정 응용하기

---

## 📖 내용
- `RequestMatcherDelegatingAuthorizationManager`의 mappings 속성은 `RequestMatcherEntry` 객체의 리스트로 구성되어 있습니다.
- mappings 속성에 직접 `RequestMatcherEntry` 객체를 추가하여 `RequestMatcher`와 `AuthorizationManager`를 매핑할 수 있습니다.
- 또한 직접 `RequestMatcherDelegatingAuthorizationManager`를 구현하여 `RequestMatcher`와 `AuthorizationManager`를 매핑할 수 있습니다.

---

## 🔍 중심 로직

```java
package org.springframework.security.web.access.intercept;

...

public final class RequestMatcherDelegatingAuthorizationManager implements AuthorizationManager<HttpServletRequest> {
    private static final AuthorizationDecision DENY = new AuthorizationDecision(false);
    private final Log logger = LogFactory.getLog(this.getClass());
    private final List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings;

    private RequestMatcherDelegatingAuthorizationManager(List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings) {
        Assert.notEmpty(mappings, "mappings cannot be empty");
        this.mappings = mappings;
    }

    /** @deprecated */
    @Deprecated
    public AuthorizationDecision check(Supplier<Authentication> authentication, HttpServletRequest request) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(LogMessage.format("Authorizing %s", requestLine(request)));
        }

        for(RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> mapping : this.mappings) {
            RequestMatcher matcher = mapping.getRequestMatcher();
            RequestMatcher.MatchResult matchResult = matcher.matcher(request);
            if (matchResult.isMatch()) {
                AuthorizationManager<RequestAuthorizationContext> manager = (AuthorizationManager)mapping.getEntry();
                if (this.logger.isTraceEnabled()) {
                    this.logger.trace(LogMessage.format("Checking authorization on %s using %s", requestLine(request), manager));
                }

                return manager.check(authentication, new RequestAuthorizationContext(request, matchResult.getVariables()));
            }
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(LogMessage.of(() -> "Denying request since did not find matching RequestMatcher"));
        }

        return DENY;
    }

    private static String requestLine(HttpServletRequest request) {
        String var10000 = request.getMethod();
        return var10000 + " " + UrlUtils.buildRequestUrl(request);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean anyRequestConfigured;
        private final List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings = new ArrayList();

        public Builder() {
        }

        public Builder add(RequestMatcher matcher, AuthorizationManager<RequestAuthorizationContext> manager) {
            Assert.state(!this.anyRequestConfigured, "Can't add mappings after anyRequest");
            Assert.notNull(matcher, "matcher cannot be null");
            Assert.notNull(manager, "manager cannot be null");
            this.mappings.add(new RequestMatcherEntry(matcher, manager));
            return this;
        }

        public Builder mappings(Consumer<List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>>> mappingsConsumer) {
            Assert.state(!this.anyRequestConfigured, "Can't configure mappings after anyRequest");
            Assert.notNull(mappingsConsumer, "mappingsConsumer cannot be null");
            mappingsConsumer.accept(this.mappings);
            return this;
        }

        public AuthorizedUrl anyRequest() {
            Assert.state(!this.anyRequestConfigured, "Can't configure anyRequest after itself");
            this.anyRequestConfigured = true;
            return new AuthorizedUrl(new RequestMatcher[]{AnyRequestMatcher.INSTANCE});
        }

        public AuthorizedUrl requestMatchers(RequestMatcher... matchers) {
            Assert.state(!this.anyRequestConfigured, "Can't configure requestMatchers after anyRequest");
            return new AuthorizedUrl(matchers);
        }

        public RequestMatcherDelegatingAuthorizationManager build() {
            return new RequestMatcherDelegatingAuthorizationManager(this.mappings);
        }

        public final class AuthorizedUrl {
            private final List<RequestMatcher> matchers;

            private AuthorizedUrl(RequestMatcher... matchers) {
                this((List)List.of(matchers));
            }

            private AuthorizedUrl(List<RequestMatcher> matchers) {
                this.matchers = matchers;
            }

            public Builder permitAll() {
                return this.access((a, o) -> new AuthorizationDecision(true));
            }

            public Builder denyAll() {
                return this.access((a, o) -> new AuthorizationDecision(false));
            }

            public Builder authenticated() {
                return this.access(AuthenticatedAuthorizationManager.authenticated());
            }

            public Builder fullyAuthenticated() {
                return this.access(AuthenticatedAuthorizationManager.fullyAuthenticated());
            }

            public Builder rememberMe() {
                return this.access(AuthenticatedAuthorizationManager.rememberMe());
            }

            public Builder anonymous() {
                return this.access(AuthenticatedAuthorizationManager.anonymous());
            }

            public Builder hasRole(String role) {
                return this.access(AuthorityAuthorizationManager.hasRole(role));
            }

            public Builder hasAnyRole(String... roles) {
                return this.access(AuthorityAuthorizationManager.hasAnyRole(roles));
            }

            public Builder hasAuthority(String authority) {
                return this.access(AuthorityAuthorizationManager.hasAuthority(authority));
            }

            public Builder hasAnyAuthority(String... authorities) {
                return this.access(AuthorityAuthorizationManager.hasAnyAuthority(authorities));
            }

            private Builder access(AuthorizationManager<RequestAuthorizationContext> manager) {
                for(RequestMatcher matcher : this.matchers) {
                    Builder.this.mappings.add(new RequestMatcherEntry(matcher, manager));
                }

                return Builder.this;
            }
        }
    }
}
```

```java
package org.springframework.security.web.util.matcher;

public class RequestMatcherEntry<T> {
    private final RequestMatcher requestMatcher;
    private final T entry;

    public RequestMatcherEntry(RequestMatcher requestMatcher, T entry) {
        this.requestMatcher = requestMatcher;
        this.entry = entry;
    }

    public RequestMatcher getRequestMatcher() {
        return this.requestMatcher;
    }

    public T getEntry() {
        return this.entry;
    }
}
```

📌

---

## 📂 참고할만한 자료

