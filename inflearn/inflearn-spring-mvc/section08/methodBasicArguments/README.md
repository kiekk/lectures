# ☘️ 메서드 기본 매개변수

---

## 📖 내용

- 스프링 MVC 에서 메서드 파라미터에 공통적으로 선언할 수 있는 기본 인자들이 있으며 요청 및 응답 처리, 세션 관리, 인증 정보 접근 등 다양한 상황에서 적절하게 사용될 수 있다
- WebRequest,NativeWebRequest, HttpSession, Principal, HttpMethod, Reader, Writer 에 대해 살펴 본다

---

### WebRequest / NativeWebRequest
- WebRequest 와 NativeWebRequest 는 웹 요청에 대한 다양한 정보를 제공하는 객체로서 HttpServletRequest 보다 더 많은 메서드와 웹 요청 전반에 쉽게 접근한다

```java
org.springframework.web.context.request.WebRequest
org.springframework.web.context.request.NativeWebRequest
```

---

### HttpSession
- HttpSession은 서버에 저장된 세션 데이터를 다룰 수 있게 해주는 객체로서 사용자의 세션 정보를 읽거나 설정할 수 있다
```java
jakarta.servlet.http.HttpSession
```

--- 

### Principal
- Principal은 현재 인증된 사용자의 정보를 나타내는 객체로서 사용자 이름이나 인증된 사용자와 관련된 데이터를 제공해 준다
- 스프링 시큐리티와 통합되어 제공하는 기능이다
```java
java.security.Principal
```

---

### HttpMethod
- HttpMethod 는 요청 메서드(GET, POST 등)를 나타내며 현재 요청이 어떤 HTTP 메서드인지 확인할 수 있다
```java
org.springframework.http.HttpMethod
```

---

### InputStream&Reader / OutputStream&Writer
- Reader는 요청 본문을 읽는 데 사용되며 Writer는 응답 본문에 데이터를 작성하는 데 사용된다
```java
java.io.InputStream
java.io.InputStreamReader
java.io.OutputStream
java.io.OutputStreamWriter
```
---

## 🔍 중심 로직

```java

```

📌

---

## 💬 코멘트

---
