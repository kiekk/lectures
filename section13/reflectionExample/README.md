# ☘️ 자바 리플렉션 실전 예제 (1) ~ (2)

---

## 📖 내용

- Java Reflection을 사용하여 객체의 필드(Field) 와 메서드(Method) 정보를 동적으로 관리하고 실행하는 기능을 제공하는 예제 작성

---

## 🔍 중심 로직

```java
// 필드 관련 작업을 관리
class ReflectionFieldManager {
    private final Map<String, Field> fieldMap = new HashMap<>();

    public ReflectionFieldManager(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            fieldMap.put(field.getName(), field);
        }
    }

    public Field getField(String fieldName) {
        return fieldMap.get(fieldName);
    }

    public void setFieldValue(Object target, String fieldName, Object value) throws IllegalAccessException {
        Field field = getField(fieldName);
        if (field != null) field.set(target, value);
    }

    public Object getFieldValue(Object target, String fieldName) throws IllegalAccessException {
        Field field = getField(fieldName);
        if (field != null) return field.get(target);
        return null;
    }
}
```

```java
// 메서드 관련 작업(객체 저장/조회)을 관리
class ReflectionMethodManager {
    private final Map<String, Method> methodMap = new HashMap<>();

    public ReflectionMethodManager(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            method.setAccessible(true);
            methodMap.put(method.getName(), method);
        }
    }

    public Method getMethod(String methodName) {
        return methodMap.get(methodName);
    }

    public Object invokeMethod(Object target, String methodName, Object... args) throws Exception {
        Method method = getMethod(methodName);
        if (method != null) {
            return method.invoke(target, args);
        }
        return null;
    }
}
```

```java
// 필드와 메서드를 실행하는 역할
class ReflectionExecutor {
    private final ReflectionFieldManager fieldManager;
    private final ReflectionMethodManager methodManager;

    public ReflectionExecutor(Class<?> clazz) {
        this.fieldManager = new ReflectionFieldManager(clazz);
        this.methodManager = new ReflectionMethodManager(clazz);
    }

    public ReflectionFieldManager getFieldManager() {
        return fieldManager;
    }

    public ReflectionMethodManager getMethodManager() {
        return methodManager;
    }
}
```

```java
public class Main {
    public static void main(String[] args) throws Exception {
        Class<?> userClass = Class.forName("io.springmvc.springmvcmaster.User");
        Constructor<?> userConstructor = userClass.getDeclaredConstructor(String.class, int.class);
        Object user = userConstructor.newInstance("Alice", 25); // 동적 User 객체 생성
        ReflectionExecutor executor = new ReflectionExecutor(userClass);
        // 필드 관리
        ReflectionFieldManager fieldManager = executor.getFieldManager();
        fieldManager.setFieldValue(user, "name", "Bob");
        // 메서드 관리
        ReflectionMethodManager methodManager = executor.getMethodManager();
        methodManager.invokeMethod(user, "greet");
        // 결과 출력
        Object updatedName = fieldManager.getFieldValue(user, "name");
        System.out.println("Updated User Name: " + updatedName);
    }
}
```

📌

---

## 💬 코멘트

---
