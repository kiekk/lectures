# ☘️ 서블릿(Servlet)

---

## 📖 내용

- 서블릿은 Jakarta EE (Enterprise Edition) 플랫폼의 핵심 기술 중 하나로 클라이언트-서버 모델에서 서버 측에서 실행되는 자바 프로그램이다.
- 주로 HTTP 요청과 응답을 처리하기 위해 사용되며 자바 서블릿 API를 통해 웹 애플리케이션 개발을 쉽게 할 수 있도록 해준다.
- `Servlet` <- `GenericServlet` <- `HttpServlet` 상속 구조로 되어 있으며,
  - 이 중 `GenericServlet`, `HttpServlet`을 상속받아 구현할 수 있다.

### 서블릿 생명 주기 (Servlet Lifecycle)
- 서블릿은 서블릿 컨테이너에 의해 클래스 로드 및 객체 생성이 이루어지며 서블릿의 생명주기는 init, service, destroy 과정을 거친다.

### 서블릿 로드 및 생성
- 서블릿 로드 - 컨테이너는 서블릿을 처음으로 요청받거나 혹은 애플리케이션이 시작될 때 서블릿을 메모리에 로드한다.
  - 지연 로딩 (Lazy Loading) - 첫 번째 클라이언트 요청이 들어올 때 서블릿이 로드되고 서블릿 객체를 생성한다.
  - 즉시 로딩 (Eager Loading) - 애플리케이션이 시작될 때 서블릿이 로드되고 객체가 생성된다.
  - 이를 위해 `<load-on-startup` 속성을 사용한다.

`<load-on-startup>` 속성
- 음수 값 또는 설정하지 않은 경우: 서블릿은 지연 로딩된다.
- 양수 값은 즉시 서블릿이 로딩되면 값이 낮을수록 더 높은 우선 순위를 가지며 1이 가장 먼저 로드된다.
- 0 값은 애플리케이션 시작 시 서블릿을 로드하지만 양수 값들보다 우선하지 않을 수 있다.

```java
@WebServlet(name = "customServlet", urlPatterns = {"/hello"}, loadOnStartup = 1)
```

### 서블릿 생명 주기
- init()
  - 서블릿이 생성되고 init 메서드를 통해 초기화 되며 최초 한 번만 호출된다
  - 주로 초기화 파라미터를 읽거나 DB 연결 설정 등 초기 설정 작업을 수행한다
- service()
  - 클라이언트로부터의 모든 요청은 service 메서드를 통해 처리되며 HttpServletRequest 와 HttpServletResponse 객체가 생성되어 전달된다
  - HTTP 메서드(GET, POST 등) 에 따라 doGet, doPost 등의 메서드를 호출한다
- destroy()
  - 서블릿이 서비스에서 제외되면 destroy() 메서드를 통해 종료되고 이후 가비지 컬렉션 및 finalize 과정을 거친다

---

## 🔍 중심 로직

```java
package jakarta.servlet;

...

public interface Servlet {

    void init(ServletConfig config) throws ServletException;

    ServletConfig getServletConfig();

    void service(ServletRequest req, ServletResponse res) throws ServletException, IOException;

    String getServletInfo();

    void destroy();
}
```

```java
package jakarta.servlet;

...

public abstract class GenericServlet implements Servlet, ServletConfig, java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private transient ServletConfig config;

    public GenericServlet() {
        // NOOP
    }

    @Override
    public void destroy() {
        // NOOP by default
    }

    @Override
    public String getInitParameter(String name) {
        return getServletConfig().getInitParameter(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return getServletConfig().getInitParameterNames();
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    public ServletContext getServletContext() {
        return getServletConfig().getServletContext();
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        this.init();
    }

    public void init() throws ServletException {
        // NOOP by default
    }

    public void log(String message) {
        getServletContext().log(getServletName() + ": " + message);
    }

    public void log(String message, Throwable t) {
        getServletContext().log(getServletName() + ": " + message, t);
    }

    @Override
    public abstract void service(ServletRequest req, ServletResponse res) throws ServletException, IOException;

    @Override
    public String getServletName() {
        return config.getServletName();
    }
}
```

```java
package jakarta.servlet.http;

...

public abstract class HttpServlet extends GenericServlet {

    ...
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cachedUseLegacyDoHead = Boolean.parseBoolean(config.getInitParameter(LEGACY_DO_HEAD));
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = lStrings.getString("http.method_get_not_supported");
        sendMethodNotAllowed(req, resp, msg);
    }

    protected long getLastModified(HttpServletRequest req) {
        return -1;
    }
    
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (DispatcherType.INCLUDE.equals(req.getDispatcherType()) || !cachedUseLegacyDoHead) {
            doGet(req, resp);
        } else {
            NoBodyResponse response = new NoBodyResponse(resp);
            doGet(req, response);
            if (req.isAsyncStarted()) {
                req.getAsyncContext().addListener(new NoBodyAsyncContextListener(response));
            } else {
                response.setContentLength();
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String msg = lStrings.getString("http.method_post_not_supported");
        sendMethodNotAllowed(req, resp, msg);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String msg = lStrings.getString("http.method_put_not_supported");
        sendMethodNotAllowed(req, resp, msg);
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String msg = lStrings.getString("http.method_delete_not_supported");
        sendMethodNotAllowed(req, resp, msg);
    }


    ... other methods

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getMethod();

        if (method.equals(METHOD_GET)) {
            long lastModified = getLastModified(req);
            if (lastModified == -1) {
                // servlet doesn't support if-modified-since, no reason
                // to go through further expensive logic
                doGet(req, resp);
            } else {
                long ifModifiedSince;
                try {
                    ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                } catch (IllegalArgumentException iae) {
                    // Invalid date header - proceed as if none was set
                    ifModifiedSince = -1;
                }
                if (ifModifiedSince < (lastModified / 1000 * 1000)) {
                    // If the servlet mod time is later, call doGet()
                    // Round down to the nearest second for a proper compare
                    // A ifModifiedSince of -1 will always be less
                    maybeSetLastModified(resp, lastModified);
                    doGet(req, resp);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                }
            }

        } else if (method.equals(METHOD_HEAD)) {
            long lastModified = getLastModified(req);
            maybeSetLastModified(resp, lastModified);
            doHead(req, resp);

        } else if (method.equals(METHOD_POST)) {
            doPost(req, resp);

        } else if (method.equals(METHOD_PUT)) {
            doPut(req, resp);

        } else if (method.equals(METHOD_DELETE)) {
            doDelete(req, resp);

        } else if (method.equals(METHOD_OPTIONS)) {
            doOptions(req, resp);

        } else if (method.equals(METHOD_TRACE)) {
            doTrace(req, resp);

        } else {
            //
            // Note that this means NO servlet supports whatever
            // method was requested, anywhere on this server.
            //

            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[1];
            errArgs[0] = method;
            errMsg = MessageFormat.format(errMsg, errArgs);

            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
        }
    }

    ... other method

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        HttpServletRequest request;
        HttpServletResponse response;

        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) res;
        } catch (ClassCastException e) {
            throw new ServletException(lStrings.getString("http.non_http"));
        }
        service(request, response);
    }

    ...
}
```

📌

---

## 💬 코멘트

---
