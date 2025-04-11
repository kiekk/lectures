# ☘️ CSRF 토큰 유지 및 검증 - 1 ~ 3

---

## 📖 내용
- `CsrfTokenRepository`
  - `CsrfToken`을 영속화 하는 역할을 담당하며, `HttpSessionCsrfTokenRepository`와 `CookieCsrfTokenRepository`, `TestCsrfTokenRepository` 를 지원합니다.

- `CsrfTokenRequestHandler`
  - `CsrfToken` 은 `CsrfTokenRequestHandler` 를 사용하여 토큰을 생성 및 응답하고 HTTP 헤더 또는 요청 매개변수로부터 토큰의 유효성을 검증합니다.
  - `XorCsrfTokenRequestAttributeHandler`와 `CsrfTokenRequestAttributeHandler`를 구현체로 제공합니다.

---

## 🔍 중심 로직

```java
package org.springframework.security.web.csrf;

...

public interface CsrfTokenRepository {
    CsrfToken generateToken(HttpServletRequest request);

    void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response);

    CsrfToken loadToken(HttpServletRequest request);

    default DeferredCsrfToken loadDeferredToken(HttpServletRequest request, HttpServletResponse response) {
        return new RepositoryDeferredCsrfToken(this, request, response);
    }
}
```

```java
package org.springframework.security.web.csrf;

...

public final class HttpSessionCsrfTokenRepository implements CsrfTokenRepository {
    private static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    private static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";
    private static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");
    private String parameterName = "_csrf";
    private String headerName = "X-CSRF-TOKEN";
    private String sessionAttributeName;

    public HttpSessionCsrfTokenRepository() {
        this.sessionAttributeName = DEFAULT_CSRF_TOKEN_ATTR_NAME;
    }

    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token == null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(this.sessionAttributeName);
            }
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(this.sessionAttributeName, token);
        }

    }

    public CsrfToken loadToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (CsrfToken)session.getAttribute(this.sessionAttributeName);
    }

    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(this.headerName, this.parameterName, this.createNewToken());
    }

    public void setParameterName(String parameterName) {
        Assert.hasLength(parameterName, "parameterName cannot be null or empty");
        this.parameterName = parameterName;
    }

    public void setHeaderName(String headerName) {
        Assert.hasLength(headerName, "headerName cannot be null or empty");
        this.headerName = headerName;
    }

    public void setSessionAttributeName(String sessionAttributeName) {
        Assert.hasLength(sessionAttributeName, "sessionAttributename cannot be null or empty");
        this.sessionAttributeName = sessionAttributeName;
    }

    private String createNewToken() {
        return UUID.randomUUID().toString();
    }
}
```

```java
package org.springframework.security.web.csrf;

...

public final class HttpSessionCsrfTokenRepository implements CsrfTokenRepository {
    private static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    private static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";
    private static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");
    private String parameterName = "_csrf";
    private String headerName = "X-CSRF-TOKEN";
    private String sessionAttributeName;

    public HttpSessionCsrfTokenRepository() {
        this.sessionAttributeName = DEFAULT_CSRF_TOKEN_ATTR_NAME;
    }

    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token == null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(this.sessionAttributeName);
            }
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(this.sessionAttributeName, token);
        }

    }

    public CsrfToken loadToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (CsrfToken)session.getAttribute(this.sessionAttributeName);
    }

    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(this.headerName, this.parameterName, this.createNewToken());
    }

    public void setParameterName(String parameterName) {
        Assert.hasLength(parameterName, "parameterName cannot be null or empty");
        this.parameterName = parameterName;
    }

    public void setHeaderName(String headerName) {
        Assert.hasLength(headerName, "headerName cannot be null or empty");
        this.headerName = headerName;
    }

    public void setSessionAttributeName(String sessionAttributeName) {
        Assert.hasLength(sessionAttributeName, "sessionAttributename cannot be null or empty");
        this.sessionAttributeName = sessionAttributeName;
    }

    private String createNewToken() {
        return UUID.randomUUID().toString();
    }
}
```

```java
package org.springframework.security.web.csrf;

...

@FunctionalInterface
public interface CsrfTokenRequestHandler extends CsrfTokenRequestResolver {
    void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken);

    default String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(csrfToken, "csrfToken cannot be null");
        String actualToken = request.getHeader(csrfToken.getHeaderName());
        if (actualToken == null) {
            actualToken = request.getParameter(csrfToken.getParameterName());
        }

        return actualToken;
    }
}
```

```java
package org.springframework.security.web.csrf;

...

public class CsrfTokenRequestAttributeHandler implements CsrfTokenRequestHandler {
    private String csrfRequestAttributeName = "_csrf";

    public CsrfTokenRequestAttributeHandler() {
    }

    public final void setCsrfRequestAttributeName(String csrfRequestAttributeName) {
        this.csrfRequestAttributeName = csrfRequestAttributeName;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> deferredCsrfToken) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(response, "response cannot be null");
        Assert.notNull(deferredCsrfToken, "deferredCsrfToken cannot be null");
        request.setAttribute(HttpServletResponse.class.getName(), response);
        CsrfToken csrfToken = new SupplierCsrfToken(deferredCsrfToken);
        request.setAttribute(CsrfToken.class.getName(), csrfToken);
        String csrfAttrName = this.csrfRequestAttributeName != null ? this.csrfRequestAttributeName : csrfToken.getParameterName();
        request.setAttribute(csrfAttrName, csrfToken);
    }

    private static final class SupplierCsrfToken implements CsrfToken {
        private final Supplier<CsrfToken> csrfTokenSupplier;

        private SupplierCsrfToken(Supplier<CsrfToken> csrfTokenSupplier) {
            this.csrfTokenSupplier = csrfTokenSupplier;
        }

        public String getHeaderName() {
            return this.getDelegate().getHeaderName();
        }

        public String getParameterName() {
            return this.getDelegate().getParameterName();
        }

        public String getToken() {
            return this.getDelegate().getToken();
        }

        private CsrfToken getDelegate() {
            CsrfToken delegate = (CsrfToken)this.csrfTokenSupplier.get();
            if (delegate == null) {
                throw new IllegalStateException("csrfTokenSupplier returned null delegate");
            } else {
                return delegate;
            }
        }
    }
}
```

```java
package org.springframework.security.web.csrf;

...

public final class XorCsrfTokenRequestAttributeHandler extends CsrfTokenRequestAttributeHandler {
    private SecureRandom secureRandom = new SecureRandom();

    public XorCsrfTokenRequestAttributeHandler() {
    }

    public void setSecureRandom(SecureRandom secureRandom) {
        Assert.notNull(secureRandom, "secureRandom cannot be null");
        this.secureRandom = secureRandom;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> deferredCsrfToken) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(response, "response cannot be null");
        Assert.notNull(deferredCsrfToken, "deferredCsrfToken cannot be null");
        Supplier<CsrfToken> updatedCsrfToken = this.deferCsrfTokenUpdate(deferredCsrfToken);
        super.handle(request, response, updatedCsrfToken);
    }

    private Supplier<CsrfToken> deferCsrfTokenUpdate(Supplier<CsrfToken> csrfTokenSupplier) {
        return new CachedCsrfTokenSupplier(() -> {
            CsrfToken csrfToken = (CsrfToken)csrfTokenSupplier.get();
            Assert.state(csrfToken != null, "csrfToken supplier returned null");
            String updatedToken = createXoredCsrfToken(this.secureRandom, csrfToken.getToken());
            return new DefaultCsrfToken(csrfToken.getHeaderName(), csrfToken.getParameterName(), updatedToken);
        });
    }

    public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
        String actualToken = super.resolveCsrfTokenValue(request, csrfToken);
        return getTokenValue(actualToken, csrfToken.getToken());
    }

    private static String getTokenValue(String actualToken, String token) {
        byte[] actualBytes;
        try {
            actualBytes = Base64.getUrlDecoder().decode(actualToken);
        } catch (Exception var8) {
            return null;
        }

        byte[] tokenBytes = Utf8.encode(token);
        int tokenSize = tokenBytes.length;
        if (actualBytes.length != tokenSize * 2) {
            return null;
        } else {
            byte[] xoredCsrf = new byte[tokenSize];
            byte[] randomBytes = new byte[tokenSize];
            System.arraycopy(actualBytes, 0, randomBytes, 0, tokenSize);
            System.arraycopy(actualBytes, tokenSize, xoredCsrf, 0, tokenSize);
            byte[] csrfBytes = xorCsrf(randomBytes, xoredCsrf);
            return Utf8.decode(csrfBytes);
        }
    }

    private static String createXoredCsrfToken(SecureRandom secureRandom, String token) {
        byte[] tokenBytes = Utf8.encode(token);
        byte[] randomBytes = new byte[tokenBytes.length];
        secureRandom.nextBytes(randomBytes);
        byte[] xoredBytes = xorCsrf(randomBytes, tokenBytes);
        byte[] combinedBytes = new byte[tokenBytes.length + randomBytes.length];
        System.arraycopy(randomBytes, 0, combinedBytes, 0, randomBytes.length);
        System.arraycopy(xoredBytes, 0, combinedBytes, randomBytes.length, xoredBytes.length);
        return Base64.getUrlEncoder().encodeToString(combinedBytes);
    }

    private static byte[] xorCsrf(byte[] randomBytes, byte[] csrfBytes) {
        Assert.isTrue(randomBytes.length == csrfBytes.length, "arrays must be equal length");
        int len = csrfBytes.length;
        byte[] xoredCsrf = new byte[len];
        System.arraycopy(csrfBytes, 0, xoredCsrf, 0, len);

        for(int i = 0; i < len; ++i) {
            xoredCsrf[i] ^= randomBytes[i];
        }

        return xoredCsrf;
    }

    private static final class CachedCsrfTokenSupplier implements Supplier<CsrfToken> {
        private final Supplier<CsrfToken> delegate;
        private CsrfToken csrfToken;

        private CachedCsrfTokenSupplier(Supplier<CsrfToken> delegate) {
            this.delegate = delegate;
        }

        public CsrfToken get() {
            if (this.csrfToken == null) {
                this.csrfToken = (CsrfToken)this.delegate.get();
            }

            return this.csrfToken;
        }
    }
}
```

📌
- `HttpSessionCsrfTokenRepository`를 기본적으로 사용하며 HTTP 요청 헤더인 `X-CSRF-TOKEN` 또는 매개변수인 `_csrf`에서 토큰을 읽습니다.
- `CookieCsrfTokenRepository`를 사용하면 CSRF 토큰을 쿠키에 저장할 수 있습니다. `XSRF-TOKEN`이라는 이름의 쿠키에 CSRF 토큰을 저장합니다. HTTP 요청 헤더인 `X-XSRF-TOKEN` 또는 매개변수인 `_csrf`에서 토큰을 읽습니다.

---

## 📂 참고할만한 자료

