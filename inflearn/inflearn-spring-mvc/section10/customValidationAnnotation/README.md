# ☘️ 커스텀 검증 어노테이션 구현하기

---

## 📖 내용

- 커스텀 검증 어노테이션은 기본적인 유효성 검증 외에 특정한 유효성 검사에 적용하기 위해 직접 어노테이션을 정의하는 방법을 말한다.

---

### 구현 방법
- 유효성 검사를 위한 새로운 어노테이션을 정의한다
- 실제 검증 로직을 수행하기 위한 ConstraintValidator 인터페이스를 구현한다
- 어노테이션에 ConstraintValidator 클래스를 연결하고 유효성 검사 시 해당 어노테이션이 ConstraintValidator 를 사용하도록 한다

- isValid()
  - 검증 작업을 수행하며 불린 값을 반환한다. 검증 대상 객체(T)는 변경하지 않고 그대로 유지해야 한다
- initialize()
  - 검증기의 isValid 호출을 준비하기 위해 초기화 한다. 검증에 사용되기 전에 먼저 호출이 된다

---

## 🔍 중심 로직

```java
package jakarta.validation;

// imports

public interface ConstraintValidator<A extends Annotation, T> {
	default void initialize(A constraintAnnotation) {
	}

	boolean isValid(T value, ConstraintValidatorContext context);
}
```

📌

---

## 💬 코멘트

---
