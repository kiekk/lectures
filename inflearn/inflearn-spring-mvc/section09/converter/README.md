# ☘️ Converter

---

## 📖 내용

- 타입변환은 바인딩을 처리하는 과정속에 포함되어 있으며 타입 변환이 실패하면 더 이상 바인딩을 진행하지 않고 오류를 발생시킨다
- Spring 의 Converter<S,T> 는 입력 데이터를 원하는 데이터 타입으로 변환하기 위한 인터페이스로서 소스 객체(S)를 대상 객체(T)로 변환하는 데 사용된다
- 스프링은 이미 수많은 컨버터 구현체들을 제공하고 있으며 특별한 타입변환이 필요할 경우 Converter 를 직접 구현해서 사용할 수 있다

---

### 문제점
- Converter 를 직접 실행하는 것이 아닌 자동적으로 타입 변환이 이루어져야 한다
- Converter 가 내부에 숨겨진 상태에서 타입변환이 이루어져야 하고 사용자는 Converter 를 알 필요가 없어야 한다

```java
@RestController
@Slf4j
public class UrlController {
    private final StringToUrlConverter stringToUrlConverter = new StringToUrlConverter();
    private final UrlToStringConverter urlToStringConverter = new UrlToStringConverter();

    @GetMapping("/url")
    public String saveUrl(@RequestParam("url") String url) {
        Url result = stringToUrlConverter.convert(url);
        log.info("Url : {}", result);
        return "URL: " + result;
    }

    @PostMapping("/url")
    public String getUrl(@ModelAttribute Url url) {
        String result = urlToStringConverter.convert(url);
        log.info("Url : {}", result);
        return "URL: " + result;
    }
}
```

---

## 🔍 중심 로직

```java
package org.springframework.core.convert.converter;

// imports

@FunctionalInterface
public interface Converter<S, T> {

	@Nullable
	T convert(S source);

	default <U> Converter<S, U> andThen(Converter<? super T, ? extends U> after) {
		Assert.notNull(after, "'after' Converter must not be null");
		return (S s) -> {
			T initialResult = convert(s);
			return (initialResult != null ? after.convert(initialResult) : null);
		};
	}

}
```

📌

---

## 💬 코멘트

---
