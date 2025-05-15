# ☘️ 서블릿 방식 - Servlet

---

## 📖 내용

- Servlet은 자바 기반의 웹 애플리케이션 개발 초기 단계에서 사용된 기술로 자바로 작성된 서버 측 프로그램으로 사용되었다.
- 클라이언트의 요청을 처리하고 동적 콘텐츠를 생성하는 데 사용되었는데 주로 HTTP 요청과 응답을 처리하며 자바 클래스 형태로 HTTP 요청을 처리한다.

### 서블릿 컨테이너에서 실행

- 서블릿이 실행되는 환경으로, 서블릿을 관리하고 요청을 처리한다.
- Apache Tomcat, Jeus, Jboss와 같은 WAS(Web Application Server) 에서 실행되는 환경이다.


### 특징

- 클래스 단위로 직접적인 HTTP 요청/응답 처리를 세밀하게 제어할 수 있다는 장점은 있다.
- 하지만 HTML과 비즈니스 로직이 하나의 서블릿 클래스 안에 혼재되어 코드가 복잡해지고 유지보수가 어려워진다.
- 서블릿 방식은 코드의 재사용성을 어렵게 한다. 즉, 동일한 비즈니스 로직을 여러 서블릿에서 반복해서 작성하게 되는 경우가 많다.

### 서블릿 기반 요청 프로세스

<img src="https://javaconceptoftheday.com/wp-content/uploads/2021/01/JavaServletArchitecture.png" width="500px">

<sub>※ 이미지 출처: https://javaconceptoftheday.com/wp-content/uploads/2021/01/JavaServletArchitecture.png</sub>

---

## 🔍 중심 로직

```java
// build.gradle
dependencies {
    implementation 'jakarta.servlet:jakarta.servlet-api:6.0.0'
}

// HelloServlet.java
@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
  private String message;

  public void init() {
    message = "Hello World!";
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");

    // Hello
    PrintWriter out = response.getWriter();
    out.println("<html><body>");
    out.println("<h1>" + message + "</h1>");
    out.println("</body></html>");
  }

  public void destroy() {
  }
}
```

📌
- 강의에서는 Spring Boot 애플리케이션으로 'spring-boot-starter-web'을 사용하여 서블릿을 구현하는 방법을 설명한다.
- 하지만 핵심은 서블릿이기 때문에 서블릿 API 의존성만을 추가하여 구현하는 방법도 있다.

---

## 💬 코멘트

- ***<span style="color: red;">모든 클래스가 서블릿일 필요는 없다.</span>***
    - 비즈니스 로직을 담당하는 부분은 서블릿이 아닌 클래스만으로 작성해도 충분하다.
    - 서블릿은 단순히 요청을 받고 응답하는 역할만 수행하면 된다.

---
