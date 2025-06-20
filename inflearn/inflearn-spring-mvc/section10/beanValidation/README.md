# ☘️ Bean Validation 개요

---

## 📖 내용

- Bean Validation은 Java 애플리케이션에서 객체의 유효성 검증을 위해 도입된 기술로서 데이터 검증 로직을 일관되게 적용할 수 있도록 하는 표준화 된 API를 제공한다
- 다양한 Java 프레임워크(Spring, Jakarta EE 등)에서 공통으로 사용할 수 있도록 JSR-303(Bean Validation 1.0)과 JSR-380(Bean Validation 2.0) 표준이 제정되었다

---

### Bean Validation & Spring 폼 검증
- Spring 폼 검증
  - 개발자가 직접 검증 로직을 작성해야 하며, 각각의 필드에 대한 조건을 수동으로 명시해야 한다
  - 검증 중 발생한 오류는 Errors 객체에 기록되며 사용자는 어떤 필드에서 어떤 오류가 발생했는지 확인할 수 있다
  - 검증 조건이 복잡하거나 커스텀 검증이 필요할 때 유용하다
- Bean Validation
  - 어노테이션을 사용해 검증 규칙을 선언적으로 정의할 수 있으며 개발자는 별도로 검증 로직을 작성할 필요가 없다
  - 객체의 필드에 선언된 어노테이션을 기반으로 자동으로 검증이 수행된다
  - 객체가 중첩된 경우에도 중첩된 객체에 대한 검증을 함께 수행할 수 있다

---

### JSR-303 / JSR-380 주요 어노테이션
- JSR-303과 JSR-380은 둘 다 Java Bean Validation의 표준 사양이지만JSR-380이 JSR-303을 확장하여 추가된 기능을 제공하는 방식으로 발전했다

- https://docs.jboss.org/hibernate/validator/9.0/reference/en-US/html_single/#section-builtin-constraints

---

### Bean Validation 사용
- 어노테이션 검증을 수행하기 위해서는 스프링 부트에서 제공하는 의존성을 추가해야한다
- Bean Validation 표준을 구현한 대표적인 기술들이 있으며 Hibernate Validator, Apache Bval 등이 있으며 주로 Hibernate Validator 를 사용한다
  - `implementation 'org.springframework.boot:spring-boot-starter-validation'`

---

## 🔍 중심 로직

```java
jakarta.validation.Validation;
jakarta.validation.Validator;
jakarta.validation.ValidatorFactory;
jakarta.validation.ConstraintViolation;
```

```java
package jakarta.validation;

// imports

public interface ConstraintViolation<T> {
    String getMessage();

    String getMessageTemplate();

    T getRootBean();

    Class<T> getRootBeanClass();

    Object getLeafBean();

    Object[] getExecutableParameters();

    Object getExecutableReturnValue();

    Path getPropertyPath();

    Object getInvalidValue();

    ConstraintDescriptor<?> getConstraintDescriptor();

    <U> U unwrap(Class<U> var1);
}
```

📌

---

## 💬 코멘트

---
