# ☘️ Model

---

## 📖 내용

- Model은 컨트롤러와 뷰 사이의 데이터를 전달하는 역할을 하며 컨트롤러에서 데이터를 Model 객체에 추가하면 그 데이터는 뷰에서 접근할 수 있게 된다
- Model 인터페이스는 주로 HTML 렌더링을 위한 데이터 보관소 역할을 하며 Map과 유사한 방식으로 동작한다
- 내부적으로 ModelMethodProcessor 클래스가 사용된다

---

### BindingAwareModelMap
- BindingAwareModelMap 은 Model 구현체로서 @ModelAttribute 로 바인딩된 객체를 가지고 있으며 바인딩 결과를 저장하는 BindingResult 를 생성하고 관리한다

<img src="image_1.png" width="350">

---

## 🔍 중심 로직

```java
package org.springframework.ui;

// imports

public interface Model {

	Model addAttribute(String attributeName, @Nullable Object attributeValue);

	Model addAttribute(Object attributeValue);

	Model addAllAttributes(Collection<?> attributeValues);

	Model addAllAttributes(Map<String, ?> attributes);

	Model mergeAttributes(Map<String, ?> attributes);

	boolean containsAttribute(String attributeName);

	@Nullable
	Object getAttribute(String attributeName);

	Map<String, Object> asMap();

}
```

```java
package org.springframework.web.method.annotation;

// imports

public class ModelMethodProcessor implements HandlerMethodArgumentResolver, HandlerMethodReturnValueHandler {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Model.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	@Nullable
	public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

		Assert.state(mavContainer != null, "ModelAndViewContainer is required for model exposure");
		return mavContainer.getModel();
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return Model.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

		if (returnValue == null) {
			return;
		}

		if (returnValue instanceof Model model) {
			mavContainer.addAllAttributes(model.asMap());
		}
		else {
			// should not happen
			throw new UnsupportedOperationException("Unexpected return type [" +
					returnType.getParameterType().getName() + "] in method: " + returnType.getMethod());
		}
	}

}
```

```java
package org.springframework.validation.support;

// imports

@SuppressWarnings("serial")
public class BindingAwareModelMap extends ExtendedModelMap {

	@Override
	public Object put(String key, @Nullable Object value) {
		removeBindingResultIfNecessary(key, value);
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ?> map) {
		map.forEach(this::removeBindingResultIfNecessary);
		super.putAll(map);
	}

	private void removeBindingResultIfNecessary(Object key, @Nullable Object value) {
		if (key instanceof String attributeName) {
			if (!attributeName.startsWith(BindingResult.MODEL_KEY_PREFIX)) {
				String bindingResultKey = BindingResult.MODEL_KEY_PREFIX + attributeName;
				BindingResult bindingResult = (BindingResult) get(bindingResultKey);
				if (bindingResult != null && bindingResult.getTarget() != value) {
					remove(bindingResultKey);
				}
			}
		}
	}

}
```

📌

---

## 💬 코멘트

---
