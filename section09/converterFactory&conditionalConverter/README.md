# ☘️ ConverterFactory & ConditionalConverter

---

## 📖 내용

### ConverterFactory
- ConverterFactory 는 클래스 계층 전체를 처리하기 위한 클래스로서 변환 로직을 따로따로 작성하지 않고 하나의 공통 로직으로 처리할 수 있도록 한다
- 예를 들어 문자열(String) 데이터를 다양한 열거형(Enum) 타입으로 변환해야 할 때 각 열거형마다 변환기를 만들 필요 없이 변환 로직을 일관되게 관리할 수 있다

```java
StringToEnumConverterFactory factory = new StringToEnumConverterFactory();

// Color 열거형 변환기 가져오기
Converter<String, Color> colorConverter = factory.getConverter(Color.class);
Color color = colorConverter.convert("red");
System.out.println("Converted Color: " + color); // 출력: Converted Color: RED

// Status 열거형 변환기 가져오기
Converter<String, Status> statusConverter = factory.getConverter(Status.class);
Status status = statusConverter.convert("active");
System.out.println("Converted Status: " + status); // 출력: Converted Status: ACTIVE
```

---

### ConditionalConverter
- ConditionalConverter 는 특정 조건이 참일 때만 Converter 를 실행하고 싶은 경우 사용할 수 있다
- 예를 들어 타겟 필드에 특정 주석이 있을 경우 Converter 를 실행하거나 타겟 클래스에 특정 메서드가 정의된 경우 변환기를 실행하고 싶을 때 사용할 수 있다

---

### TypeDescriptor
- 객체의 타입과 관련된 부가적인 정보를 표현하기 위해 사용된다 (클래스 타입 정보, 제네릭 정보, 주석(Annotation), 배열&컬렉션 요소 타입)

```java
public class Example {
    @Deprecated
    private List<String> names = new ArrayList<>();
}

// 클래스 타입 정보
TypeDescriptor typeDescriptor = TypeDescriptor.forObject(new Example().names);
System.out.println(typeDescriptor.getType()); 
// 출력 : List.class

// 제네릭 정보
TypeDescriptor descriptor = TypeDescriptor.nested(Example.class.getDeclaredField("names"), 0);
System.out.println(descriptor.getElementTypeDescriptor().getType()); 
// 출력: class java.lang.String

// 주석 정보
TypeDescriptor descriptor = TypeDescriptor.nested(Example.class.getDeclaredField("names"), 0);
System.out.println(descriptor.getAnnotation(Deprecated.class));
// 출력: @java.lang.Deprecated(forRemoval=false, since="")
```

---

## 🔍 중심 로직

```java
package org.springframework.core.convert.converter;

public interface ConverterFactory<S, R> {

	<T extends R> Converter<S, T> getConverter(Class<T> targetType);

}
```

```java
package org.springframework.core.convert.converter;

// imports

public interface ConditionalConverter {

	boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType);

}
```

📌

---

## 💬 코멘트

---
