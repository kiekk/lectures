## ☘️ 스프링의 통합 예외 전략 개요

---

## 📖 내용

### ErrorPage & BasicErrorController 예외 전략
- 오류 발생 시 WAS 는 오류 정보를 담은 ErrorPage 를 사용해서 /error 로 요청을 포워딩 하고 BasicErrorController 가 그 요청을 받아 적절한오류 처리를 한다
- text/html 방식의 요청이 들어오면 사용자 정의 오류 페이지(HTML)를 반환 하고 REST API 방식의 요청이 들어오면 JSON 형태로 오류 정보를 반환 한다
- 템플릿이나 정적 리소스에 오류 페이지를 구성해 놓으면 오류 상태 코드나 예외에 따라 자동으로 적절한 오류 화면을 반환하거나 데이터를 반환한다

---

### ErrorPage & BasicErrorController 한계점
- WAS 의 ErrorPage 는 주로 정적인 HTML 페이지 또는 JSP로 연결되며 동적인 데이터나 사용자 정의 응답을 제공하기 어렵다
- BasicErrorController 는 예외를 전역적으로 처리하지만 특정 컨트롤러나 요청 경로에 따른 세분화된 처리 로직을 구현하기 어렵다
- REST API에서 특정 예외(예: 인증 오류, 회원 오류, 주문 오류)에 대해 메시지와 상태 코드를 반환 하려면 개별적인 오류 형태 구현이 필요하다

---

### ErrorPage & BasicErrorController 오류 처리 과정
1. 클라이언트 요청 → WAS 로 요청 전달
2. WAS → DispatcherServlet 요청 전달
3. DispatcherServlet → 컨트롤러 매핑
4. 컨트롤러에서 예외 발생
5. WAS 까지 예외가 전파되어 예외를 처리 → /error로 포워딩
6. BasicErrorController 에서 /error 요청을 받고 JSON 또는 HTML 오류 화면 생성하고 클라이언트로 응답


### 개선 된 오류 처리 과정
1. 클라이언트 요청 → WAS 로 요청 전달
2. WAS → DispatcherServlet 요청 전달
3. DispatcherServlet → 컨트롤러 매핑
4. 컨트롤러에서 예외 발생
5. 스프링 MVC 의 흐름 안에서 예외 해결
6. ~~WAS 까지 예외가 전파되어 예외를 처리 → /error로 포워딩~~
7. ~~BasicErrorController 에서 /error 요청을 받고 JSON 또는 HTML 오류 화면 생성하고 클라이언트로 응답~~

#### 더 강력한 스프링 예외 전략 제공
- 특정 예외마다 다른 처리 로직을 구현할 수 있으며 예외 유형별로 HTTP 상태 코드, 응답 메시지, 추가 데이터 등을 원하는 대로 설정할 수 있다
- WAS 의 오류 처리 메커니즘에 의존하지 않고 애플리케이션 코드 내에서 예외를 처리하고 응답을 반환할 수 있다
- 스프링 MVC 에서 발생한 예외는 HandlerExceptionResolver 클래스가 해결하도록 한다

---

## 🔍 중심 로직

```java
package org.springframework.web.servlet;

// imports

public interface HandlerExceptionResolver {

	@Nullable
	ModelAndView resolveException(
			HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex);

}
```

📌

---

## 💬 코멘트

---
