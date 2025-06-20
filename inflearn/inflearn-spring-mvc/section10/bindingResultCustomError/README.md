# ☘️ BindingResult 사용자 정의 오류 추가 - FieldError, ObjectError

---

## 📖 내용

- BindingResult 은 문자 그대로 바인딩한 결과를 담는 객체라 할 수 있다. 즉 성공한 바인딩 결과와 실패한 오류 결과를 저장한다(두 결과를 동시에 저장할 수 는 없다)
- 우리의 주 관심사는 오류 결과이며 그 오류 정보에 어떻게 접근할 수 있는지와 그리고 어떻게 커스텀하게 오류를 추가 할 수 있는지 관련 API 들을 살펴 보는 것이다
- 참고로 BindingResult 는 @ModelAttribute 가 선언되어 있는 객체를 대상으로 오류를 검증한다. 단순 기본형이나 문자열은 검증 대상이 아니다. 이것은 DataBinder 가 객체를 대상으로
  바인딩을 시도한다는 사실을 알고 있다면 쉽게 이해가 갈 것이다

---

### BindingResult API
- `addError(ObjectError error)`
  - addError() 는 필수 값 누락, 길이 제한 등 어떤 조건이 맞지 않을 경우 오류를 추가할 수 있는 API 로서 인자 값으로는 ObjectError 와 FieldError 객체가 올 수 있다
  - 스프링은 바인딩 오류 시 내부적으로 BindingResult 의 addError() API 를 사용해서 오류 정보를 저장하고 있으며 이 때 FieldError 객체를 생성해서 전달한다
  - addError() API 를 통해 추가된 FieldError, ObjectError 는 BindingResult 의 errors 속성에 저장 된다

---

### 객체 오류(글로벌 오류) 와 필드 오류
- 스프링은 오류를 추가할 때 객체 오류(or 글로벌 오류) 와 필드 오류로 구분하도록 API 를 설계했다
- 객체 오류라는 것은 말 그대로 객체 수준에서 오류를 표현한다는 의미이고 필드 오류라는 것은 객체 보다 좀 더 구체적인 필드 수준에서 오류를 표현한다는 의미이다
- 오류는 사용자나 클라이언트에게 이해하기 쉬운 문장으로 설명해야 되며 상황에 맞게끔 구체적인 오류와 종합적인 오류를 잘 조합해서 나타내야 한다


- 필드 수준 오류 (FieldError)
  - 구체적으로 어떤 객체의 어떤 필드에서 오류가 났는지를 표현한다
- 객체 수준 오류 (ObjectError)
  - 필드가 아닌 객체 혹은 전역 수준에서의 오류 사항을 표현한다

---

## 🔍 중심 로직

```java
package org.springframework.validation;

// imports

@SuppressWarnings("serial")
public class FieldError extends ObjectError {

	private final String field;

	@Nullable
	private final Object rejectedValue;

	private final boolean bindingFailure;

	public FieldError(String objectName, String field, String defaultMessage) {
		this(objectName, field, null, false, null, null, defaultMessage);
	}

	public FieldError(String objectName, String field, @Nullable Object rejectedValue, boolean bindingFailure,
			@Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage) {

		super(objectName, codes, arguments, defaultMessage);
		Assert.notNull(field, "Field must not be null");
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.bindingFailure = bindingFailure;
	}

	public String getField() {
		return this.field;
	}

	@Nullable
	public Object getRejectedValue() {
		return this.rejectedValue;
	}

	public boolean isBindingFailure() {
		return this.bindingFailure;
	}


	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (!super.equals(other)) {
			return false;
		}
		return (other instanceof FieldError otherError && getField().equals(otherError.getField()) &&
				ObjectUtils.nullSafeEquals(getRejectedValue(), otherError.getRejectedValue()) &&
				isBindingFailure() == otherError.isBindingFailure());
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		hashCode = 29 * hashCode + getField().hashCode();
		hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(getRejectedValue());
		hashCode = 29 * hashCode + (isBindingFailure() ? 1 : 0);
		return hashCode;
	}

	@Override
	public String toString() {
		// We would preferably use ObjectUtils.nullSafeConciseToString(rejectedValue) here but
		// keep including the full nullSafeToString representation for backwards compatibility.
		return "Field error in object '" + getObjectName() + "' on field '" + this.field +
				"': rejected value [" + ObjectUtils.nullSafeToString(this.rejectedValue) + "]; " +
				resolvableToString();
	}

}
```

```java
package org.springframework.validation;

// imports

@SuppressWarnings("serial")
public class ObjectError extends DefaultMessageSourceResolvable {

	private final String objectName;

	@Nullable
	private transient Object source;
    
	public ObjectError(String objectName, @Nullable String defaultMessage) {
		this(objectName, null, null, defaultMessage);
	}

	public ObjectError(
			String objectName, @Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage) {

		super(codes, arguments, defaultMessage);
		Assert.notNull(objectName, "Object name must not be null");
		this.objectName = objectName;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public void wrap(Object source) {
		if (this.source != null) {
			throw new IllegalStateException("Already wrapping " + this.source);
		}
		this.source = source;
	}

	public <T> T unwrap(Class<T> sourceType) {
		if (sourceType.isInstance(this.source)) {
			return sourceType.cast(this.source);
		}
		else if (this.source instanceof Throwable throwable) {
			Throwable cause = throwable.getCause();
			if (sourceType.isInstance(cause)) {
				return sourceType.cast(cause);
			}
		}
		throw new IllegalArgumentException("No source object of the given type available: " + sourceType);
	}

	public boolean contains(Class<?> sourceType) {
		return (sourceType.isInstance(this.source) ||
				(this.source instanceof Throwable throwable && sourceType.isInstance(throwable.getCause())));
	}


	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || other.getClass() != getClass() || !super.equals(other)) {
			return false;
		}
		ObjectError otherError = (ObjectError) other;
		return getObjectName().equals(otherError.getObjectName());
	}

	@Override
	public int hashCode() {
		return (29 * super.hashCode() + getObjectName().hashCode());
	}

	@Override
	public String toString() {
		return "Error in object '" + this.objectName + "': " + resolvableToString();
	}

}
```

📌

---

## 💬 코멘트

---
