# ☘️ ResponseEntity<T>

---

## 📖 내용

- ResponseEntity<T> 는 HTTP 응답을 나타내는 클래스로서 주로 응답 상태 코드, 헤더, 본문을 제어하고 반환하는 데 사용되며 HttpEntity<T> 를 상속하고 있다
- ResponseEntity<T> 는 @ResponseBody 와 비슷하지만 @ResponseBody 는 메서드 반환 값을 HTTP 응답 본문으로 기록하는 반면 ResponseEntity 는 상태 코드와 헤더 그리고 본문
  까지 세밀하게 제어할 수 있는 기능을 제공한다
- ResponseEntity<T> 는 @RestController 나 @ResponseBody 가 없어도 적절한 HTTP 응답을 반환할 수 있다

---

### 흐름도
![image_1.png](image_1.png)
<sub>출처: 인프런</sub>

---

## 🔍 중심 로직

```java
package org.springframework.http;

// imports

public class ResponseEntity<T> extends HttpEntity<T> {

	private final HttpStatusCode status;

    // methods
}
```

📌

---

## 💬 코멘트

---
