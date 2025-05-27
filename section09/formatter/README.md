# ☘️ Formatter

---

## 📖 내용

- 앞에서 학습한 Converter 는 범용 타입 변환 시스템이다. 즉 타입에 제한 없이 데이터 타입 간 일반적인 변환을 다루는데 목적이 있다
- Formatter 는 특정한 환경(예: 웹 애플리케이션) 에서 데이터를 특정 형식에 맞게 변환하거나 특정 형식에서 객체로 변환하는 것에 목적이 있다
- Converter 는 로컬화(Localization) 를 고려하지 않지만 Formatter 는 지역(Locale) 에 따라 데이터 표현방식을 다르게 처리할 수 있다
- Converter 가 주로 서버 내부 데이터 변환에 사용된다면 Formatter 는 뷰(View) 와 클라이언트 간 데이터 변환에 사용된다고 볼 수 있다

![image_1.png](image_1.png)
<sub>출처: 인프런</sub>

---

## 🔍 중심 로직

```java
package org.springframework.format;

public interface Formatter<T> extends Printer<T>, Parser<T> {

}
```

```java
package org.springframework.format;

// imports

@FunctionalInterface
public interface Printer<T> {

	String print(T object, Locale locale);

}
```

```java
package org.springframework.format;

// imports

@FunctionalInterface
public interface Parser<T> {

	T parse(String text, Locale locale) throws ParseException;

}
```

📌

---

## 💬 코멘트

---
