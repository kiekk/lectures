# ☘️ HttpServletRequest 요청 처리

---

## 📖 내용

- HttpServletRequest는 클라이언트의 다양한 데이터 포맷과 요청 유형에 따라 정보를 읽고 처리할 수 있는 API를 제공한다.
- 요청을 처리하는 방식으로 URL 쿼리 파라미터, 폼 데이터 (GET/POST), REST API 처리 등 세 가지로 나누어 구분할 수 있다.


### URL 쿼리 파라미터

- URL 쿼리 파라미터는 HTTP 요청의 쿼리 문자열 (Query String) 에 포함되어 전달되며 이는 보통 GET 요청에서 사용된다.

```java
request.getParameter("paramName"); // 단일 파라미터
request.getParameterValues("paramName"); // 다중 파라미터를 배열로 반환
request.getParameterMap(); // 모든 파라미터를 Map<String, String[]> 형태로 반환
```

- URL에 민감한 정보를 포함하면 보안 위험이 있기 때문에 HTTPS를 사용해 암호화를 보장해야 한다.
- 브라우저마다 URL 길이에 제한이 있으므로 대용량 데이터를 쿼리 파라미터로 전달하기 어렵다.

### 폼 데이터 처리

- FORM 데이터는 HTML <form> 태그를 통해 클라이언트에서 서버로 전달되며 데이터는 요청 메서드에 따라 다르게 처리된다

- GET 방식
  - 요청 데이터가 URL의 쿼리 문자열에 포함된다
  - 전송 데이터의 크기는 URL 길이 제한에 의해 제약을 받지 않고 URL이 노출되므로 민감한 데이터 전송에 적합하지 않다
- POST 방식
  - 요청 데이터가 HTTP 요청 본문에 포함된다
  - Content-Type
    - application/x-www-form-urlencoded - 기본 폼 데이터 전송 방식으로서 키-값 쌍이 URL 인코딩된 형태로 전달된다
    - Body: name=leaven&hobby=reading&hobby=writing


### REST API 데이터 처리

- REST API 요청은 클라이언트가 JSON 또는 Plain Text 형태의 데이터를 HTTP 요청 본문에 포함하여 서버로 전송하는 방식으로서 getParameter() 와 같은 방법이 아닌 InputStream 으로부터 본문 데이터를 직접 읽어야 한다.

- JSON 데이터
  - Content-Type: application/json
  - 요청 본문에 JSON 형식으로 데이터를 포함한다
  - JSON 데이터를 처리하려면 요청 본문을 파싱해야 한다
- Plain Text 데이터
  - Content-Type: text/plain
  - 요청 본문에 단순 문자열 데이터를 포함한다

```java
request.getReader(); // 요청 본문을 문자 스트림으로 읽는다
request.getInputStream(); // 요청 본문을 바이너리 스트림으로 읽는다
```

---

## 🔍 중심 로직

```java
```

📌

---

## 💬 코멘트

---
