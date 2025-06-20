# ☘️ @RequestHeader & @RequestAttribute & @CookieValue

---

## 📖 내용

### @RequestHeader

- 클라이언트의 요청 헤더를 컨트롤러의 메서드 인자에 바인딩 하기 위해 @RequestHeader 애노테이션을 사용할 수 있다
- RequestHeaderMethodArgumentResolver 클래스가 사용된다

- 특정한 헤더를 바인딩해야 할 때 @RequestHeader(“헤더명”) 이라고 정의하면 된다
  - `@RequestHeader("Accept-Encoding") String encoding, @RequestHeader("Keep-Alive") long keepAlive`
- 바인딩이 필요한지 여부와 기본 값을 주고자 할 때 required 와 defaultValue 속성을 지정하면 된다
  - `@RequestHeader("Accept-Encoding", required=false, defaultValue="none")`
- Map<String, String> 타입으로 바인딩할 수 있다
  - 모든 헤더가 Map<String, String> 타입으로 바인딩된다
  - `@RequestHeader Map<String, String> headers`
- MultiValueMap<String, String> 타입으로 바인딩할 수 있다
  - 구현체로 HttpHeaders 가 있다
  - `@RequestHeader MultiValueMap<String, String> headers`
- 쉼표로 구분된 문자열을 배열이나 List 타입으로 변환
  - `@RequestHeader("Accept") List<String> acceptHeaders`

---

### @RequestAttribute

- HTTP 요청 속성(request attribute)을 메서드 파라미터에 바인딩할 때 사용하는 어노테이션으로서 주로 필터나 인터셉터에서 설정한 값을 컨트롤러 메서드에서 사용할 때 유용하다
- RequestAttributeMethodArgumentResolver 클래스가 사용 된다

`@RequestAttribute("myAttribute") String myAttribute)`

---

### @CookieValue
- HTTP 요청의 쿠키 값을 메서드 파라미터에 바인딩할 때 사용하는 애노테이션으로서 클라이언트에서 전송한 쿠키 값을 쉽게 받아 처리할 수 있다
- @CookieValue 는 특정 쿠키의 값을 메서드 파라미터로 전달하며 기본값을 설정하거나 쿠키가 존재하지 않을 때 예외를 처리할 수 있는 옵션을 제공한다
- ServletCookieValueMethodArgumentResolver 클래스가 사용 된다

`@CookieValue(value = "userSession", defaultValue = "defaultSession") String session`

---

## 🔍 중심 로직

```java
package org.springframework.web.bind.annotation;

// imports

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestHeader {

	@AliasFor("name")
	String value() default "";

	@AliasFor("value")
	String name() default "";

	boolean required() default true;

	String defaultValue() default ValueConstants.DEFAULT_NONE;

}
```

```java
package org.springframework.web.bind.annotation;

// imports

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestAttribute {

	@AliasFor("name")
	String value() default "";

	@AliasFor("value")
	String name() default "";

	boolean required() default true;

}
```

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
