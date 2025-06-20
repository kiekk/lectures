# ☘️ Validator (1) ~ (2)

---

## 📖 내용

- Spring 의 Validator 는 객체의 유효성을 검증하기 위해 설계된 인터페이스로 데이터 유효성 검증을 단순화하면서 비즈니스 로직과 검증 로직을 분리하는 데 사용된다.


- supports()
  - 해당 Validator가 특정 객체 타입을 지원하는지 확인하는 메서드이다
  - 이 메서드는 일반적으로 이렇게 구현된다
  - Foo.class.isAssignableFrom(clazz) - Foo는 실제로 검증할 객체 인스턴스의 클래스 또는 상위 클래스이다
- validate()
  - 주어진 대상 객체가 supports(Class) 메서드에서 true를 반환하는 클래스여야만 검증할 수 있다
  - 실제 유효성 검사를 수행하는 메서드이며 유효성 검사에 실패한 경우 Errors 객체를 사용하여 오류를 추가한다

---

### @InitBinder 와 Validator
- @InitBinder 의 WebDataBinder 객체는 호출될 때 마다 새롭게 생성되는 객체로서 해당 컨트롤러에서만 binder 의 속성이 유지된다
- @ModelAttribute 앞에 @Validated 어노테이션이 선언 되어 있으면 WebDataBinder 가 가지고 있는 검증기들을 실행시며 검증하게 된다
- @InitBinder("user") 에서 "user" 를 지정하게 되면 @ModelAttribute("user") 에서 지정한 "user" 와 동일한 경우에만 호출된다

- 여러 개의 @InitBinder 와 Validator 를 사용 할 경우엔 setValidator 가 아닌 addValidators 를 사용해서 Validator 를 추가해야 한다

```java
@Controller
public class UserController {
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private AdminValidator adminValidator;

    @InitBinder("user")
    protected void initBinder1(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @InitBinder("admin")
    protected void initBinder2(WebDataBinder binder) {
        binder.addValidators(adminValidator);
    }

    @PostMapping("/users")
    public String registerUser(@Validated @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "userForm";
        }
        return "redirect:/success";
    }

    @PostMapping("/admin")
    public String registerAdmin(@Validated @ModelAttribute("admin") Admin admin, BindingResult result) {
        if (result.hasErrors()) {
            return "userForm";
        }
        return "redirect:/success";
    }
}
```

- @InitBinder("user"), @InitBinder(“admin") 과 같이 @ModelAttribute에서 지정한 모델명을 기준으로 서로 구분해서 지정하고 Validator 도 구분해서 설정한다
- /users 를 호출하면 @InitBinder("user") 와 userValidator 가 호출되고 /admin 을 호출하면 @InitBinder(“admin") 와 adminValidator 가 호출된다

---

## 🔍 중심 로직

```java
package org.springframework.validation;

// imports

public interface Validator {

    boolean supports(Class<?> clazz);

    void validate(Object target, Errors errors);

    default Errors validateObject(Object target) {
        Errors errors = new SimpleErrors(target);
        validate(target, errors);
        return errors;
    }

    static <T> Validator forInstanceOf(Class<T> targetClass, BiConsumer<T, Errors> delegate) {
        return new TypedValidator<>(targetClass, targetClass::isAssignableFrom, delegate);
    }
    
    static <T> Validator forType(Class<T> targetClass, BiConsumer<T, Errors> delegate) {
        return new TypedValidator<>(targetClass, targetClass::equals, delegate);
    }

}
```

📌

---

## 💬 코멘트

---
