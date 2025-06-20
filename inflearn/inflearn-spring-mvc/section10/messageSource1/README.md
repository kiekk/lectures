# ☘️ BindingResult 와 MessageSource 연동 (1)

---

## 📖 내용

- DataBinder 의 바인딩 시 발생한 오류나 BindingResult 의 유효성 검증 오류가 발생했을 때 MessageSource 를 사용해서 해당 오류메시지를 사용자에게 제공할 수 있다
- 이 방식은 유효성 검증에 필요한 오류 메시지를 외부 파일(properties 파일 등)에서 검색 및 관리할 수 있다. 즉 오류 메시지를 MessageSource 에 위임한다고 보면 된다

```java
bindingResult.addError(new FieldError("order", "price", order.getPrice(), false,
new String[]{"range.order.price"}, new Object[]{100, 10000}, " "));
```

```properties
# validation.properties
range.order.price=가격은 {0} 이상 {1} 이하여야 합니다.
required.order.item=상품명은 필수입니다
```

---

## 🔍 중심 로직

```java
```

📌

---

## 💬 코멘트

---
