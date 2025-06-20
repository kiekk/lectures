# ☘️ @SessionAttribute

---

## 📖 내용

- @SessionAttribute는 세션에 저장된 특정 속성을 메서드 파라미터로 가져오기 위해 사용되는 어노테이션이다
- 세션에 저장된 속성 값을 컨트롤러의 메서드에서 직접 접근할 수 있도록 해주며 전역적으로 관리되는 세션 속성에 접근할 때 유용하게 사용할 수 있다

---

## 🔍 중심 로직

```java
package org.springframework.web.bind.annotation;

// imports

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionAttribute {

	@AliasFor("name")
	String value() default "";

	@AliasFor("value")
	String name() default "";

	boolean required() default true;

}
```

📌

---

## 💬 코멘트

---
