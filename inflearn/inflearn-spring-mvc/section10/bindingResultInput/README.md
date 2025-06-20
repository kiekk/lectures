# ☘️ BindingResult 입력 값 보존하기

---

## 📖 내용

- FieldError 및 ObjectError 클래스는 두 개의 생성자가 있으며 이 두 생성자는 오류의 상세 정도에 따라 사용되며 두 번째 생성자는 보다 세부적인 오류 정보를 포함한다
- 첫 번째 생성자는 객체 이름, 필드 이름, 그리고 기본 오류 메시지를 사용해 FieldError를 생성한다. 이때 거부된 값(rejectedValue)이나 다른 상세 정보(바인딩 실패 여부, 메시지 코드 등)는
  포함하지 않는다

---

### FieldError 생성자 API

```java
public ObjectError(
        String objectName, @Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage) {

    super(codes, arguments, defaultMessage);
    Assert.notNull(objectName, "Object name must not be null");
    this.objectName = objectName;
}

public FieldError(String objectName, String field, @Nullable Object rejectedValue, boolean bindingFailure,
                  @Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage) {

    super(objectName, codes, arguments, defaultMessage);
    Assert.notNull(field, "Field must not be null");
    this.field = field;
    this.rejectedValue = rejectedValue;
    this.bindingFailure = bindingFailure;
}
```

- objectName: 오류가 발생한 객체 이름
- field: 오류가 발생한 필드 이름
- rejectedValue: 클라이언트가 입력한 잘못된 값
- bindingFailure: 데이터 바인딩 실패 여부 (true면 바인딩 실패, 스프링에서 바인딩 하다가 난 오류인지 아닌지 구분할 수 있다)
- codes: 오류 코드 목록 (메시지 소스에서 사용)
- arguments: 메시지에 사용될 인자 목록
- defaultMessage: 기본 오류 메시지
---

### Thymeleaf에서 오류 메시지 출력
- Thymeleaf 템플릿에서 오류 메시지를 출력할 때는 `#fields.hasErrors(key)`로 해당 필드에 오류가 있는지 확인하고, `th:errors` 속성을 사용하여 오류 메시지를 출력할 수 있다
- #fields 는 BindingResult 를 감싸고 있는 Thymeleaf 유틸리티 객체이며 BindingResult 의 필드 및 글로벌 오류에 쉽게 접근할 수 있다

```html
<form th:action="@{/order}" th:object="${order}" method="post">
    <!-- 글로벌 오류 (ObjectError) 출력 -->
    <div th:if="${#fields.hasGlobalErrors()}">
        <p th:each="error : ${#fields.globalErrors()}" th:text="${error}">글로벌 오류 메시지</p>
    </div>
    <!-- 상품명 -->
    <label>상품명: <input type="text" th:field="*{productName}"/></label>
    <span th:if="${#fields.hasErrors('productName')}" th:errors="*{productName}">상품명 오류</span><br/>
    <!-- 수량 -->
    <label>수량: <input type="number" th:field="*{quantity}"/></label>
    <span th:if="${#fields.hasErrors('quantity')}" th:errors="*{quantity}">수량 오류</span><br/>
    <!-- 가격 -->
    <label>가격: <input type="text" th:field="*{price}"/></label>
    <span th:if="${#fields.hasErrors('price')}" th:errors="*{price}">가격 오류</span><br/>
    <button type="submit">제출</button>
</form>
```

---

## 🔍 중심 로직

```java
```

📌

---

## 💬 코멘트

---
