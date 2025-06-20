# ☘️ HttpServletResponse 응답 처리

---

## 📖 내용

- HttpServletResponse 는 응답을 처리하는 방식으로 단순 텍스트 응답, HTML 화면 처리 응답, HTTP 본문 응답 등 세 가지로 나누어 구분 할 수 있다
- 스프링에서도 응답 패턴이 이 세 가지 범주에서 크게 벗어나지 않으며 사용하기 쉽게 추상화 해서 제공하고 있다

---

## 🔍 중심 로직

```java
@WebServlet(name = "htmlResponseServlet", urlPatterns = "/htmlResponse")
public class HtmlResponseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Content-Type 지정
        response.setContentType("text/html;charset=UTF-8");

        // PrintWriter 획득
        PrintWriter out = response.getWriter();

        // HTML 응답
        // """ 로 변경
        out.println("""
                <!DOCTYPE html>)
                <html>
                <head>
                    <meta charset='UTF-8' />
                    <title>HTML 응답 예제</title>
                </head>
                <body>
                    <h1>HTML 화면 처리 예제</h1>
                    <p>이 영역은 HTML 태그를 통해 표현됩니다.</p>
                </body>
                </html>
                """);

        // 자원 정리
        out.close();
    }
}
```

```java
@WebServlet(name = "JsonResponseServlet", urlPatterns = "/jsonResponse")
public class JsonResponseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // JSON 응답을 위해 Content-Type 설정
        response.setContentType("application/json;charset=UTF-8");

        // Jackson ObjectMapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // 예시 데이터 준비(임의 생성)
        Message message = new Message("JSON Title", "Hello, JSON Response!", 123);

        // 자바 객체 -> JSON 문자열 변환
        String jsonString = objectMapper.writeValueAsString(message);

        // PrintWriter 로 응답 전송
        PrintWriter out = response.getWriter();
        out.print(jsonString);
        out.close();
    }
}
```

```java
@WebServlet(name = "simpleTextResponseServlet", urlPatterns = "/textResponse")
public class SimpleTextResponseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Content-Type 지정
        response.setContentType("text/plain;charset=UTF-8");

        // PrintWriter 획득
        PrintWriter out = response.getWriter();

        // 단순 텍스트 응답
        out.write("안녕하세요! 이것은 단순 텍스트 응답 예제입니다.");

        // 자원 정리
        out.close();
    }
}
```

📌

---

## 💬 코멘트

---
