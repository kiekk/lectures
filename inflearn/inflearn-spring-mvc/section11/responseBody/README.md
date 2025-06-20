# ☘️ @ResponseBody

---

## 📖 내용

- @ResponseBody 는 메서드의 반환 값을 HTTP 응답 본문에 직접 직렬화하여 클라이언트에 전송하며 HttpMessageConverter 를 사용하여 변환이 이루어진다
- 일반적으로 컨트롤러는 HTTP 요청을 처리하고 뷰(View)를 반환하는 방식으로 동작하는데 @ResponseBody 를 사용하면 뷰를 반환하는 대신 메서드가 반환하는 객체를 바로 HTTP 응답
  본문에 직렬화하여 전송하게 된다

---

### @ResponseBody & @RestController
- ResponseBody 는 메서드뿐만 아니라 클래스 수준에서도 사용될 수 있으며 이와 같은 효과를 가진 것이 바로 @RestController 라 할 수 있다
- @RestController는 @Controller 와 @ResponseBody 를 결합한 메타 어노테이션으로서 @RestController 가 선언된 클래스는 모든 메서드에서 반환되는 값이 자동으로
  @ResponseBody 처럼 처리가 이루어진다

---

### HTTP 본문 응답 흐름도
![image_1.png](image_1.png)
<sub>출처: 인프런</sub>

---

## 🔍 중심 로직

```java

```

📌

---

## 💬 코멘트

---
