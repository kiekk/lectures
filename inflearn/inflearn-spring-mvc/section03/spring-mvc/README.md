# ☘️ 스프링 MVC 간단 예제

---

## 📖 내용

- 스프링 MVC를 사용하면 간단한 설정으로도 MVC 패턴을 구현할 수 있습니다.
```yaml
spring:
  mvc:
    view:
      prefix: /WEB-INF/jsp/
      suffix: .jsp
```

- prefix, suffix를 설정하면 view 파일을 찾을 때 자동으로 붙여줍니다.
- 이를 처리하는 객체가 `InternalResourceViewResolver`, `UrlBasedViewResolver`입니다.

---

## 🔍 중심 로직

```java
package org.springframework.web.servlet.view;

...
public class InternalResourceViewResolver extends UrlBasedViewResolver {

  ...
  
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		InternalResourceView view = (InternalResourceView) super.buildView(viewName);
		if (this.alwaysInclude != null) {
			view.setAlwaysInclude(this.alwaysInclude);
		}
		view.setPreventDispatchLoop(true);
		return view;
	}

}
```

```java
package org.springframework.web.servlet.view;

...

public class UrlBasedViewResolver extends AbstractCachingViewResolver implements Ordered {

    /**
     * Prefix for special view names that specify a redirect URL (usually
     * to a controller after a form has been submitted and processed).
     * Such view names will not be resolved in the configured default
     * way but rather be treated as special shortcut.
     */
    public static final String REDIRECT_URL_PREFIX = "redirect:";

    /**
     * Prefix for special view names that specify a forward URL (usually
     * to a controller after a form has been submitted and processed).
     * Such view names will not be resolved in the configured default
     * way but rather be treated as special shortcut.
     */
    public static final String FORWARD_URL_PREFIX = "forward:";


    @Nullable
    private Class<?> viewClass;

    private String prefix = "";

    private String suffix = "";
    
  ...

  @Override
  @Nullable
  protected View createView(String viewName, Locale locale) throws Exception {
    // If this resolver is not supposed to handle the given view,
    // return null to pass on to the next resolver in the chain.
    if (!canHandle(viewName, locale)) {
      return null;
    }

    // Check for special "redirect:" prefix.
    if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
      String redirectUrl = viewName.substring(REDIRECT_URL_PREFIX.length());
      RedirectView view = new RedirectView(redirectUrl,
              isRedirectContextRelative(), isRedirectHttp10Compatible());
      String[] hosts = getRedirectHosts();
      if (hosts != null) {
        view.setHosts(hosts);
      }
      return applyLifecycleMethods(REDIRECT_URL_PREFIX, view);
    }

    // Check for special "forward:" prefix.
    if (viewName.startsWith(FORWARD_URL_PREFIX)) {
      String forwardUrl = viewName.substring(FORWARD_URL_PREFIX.length());
      InternalResourceView view = new InternalResourceView(forwardUrl);
      return applyLifecycleMethods(FORWARD_URL_PREFIX, view);
    }

    // Else fall back to superclass implementation: calling loadView.
    return super.createView(viewName, locale);
  }
  
  ...

  protected AbstractUrlBasedView buildView(String viewName) throws Exception {
    AbstractUrlBasedView view = instantiateView();
    view.setUrl(getPrefix() + viewName + getSuffix());
    view.setAttributesMap(getAttributesMap());

    String contentType = getContentType();
    if (contentType != null) {
      view.setContentType(contentType);
    }

    String requestContextAttribute = getRequestContextAttribute();
    if (requestContextAttribute != null) {
      view.setRequestContextAttribute(requestContextAttribute);
    }

    Boolean exposePathVariables = getExposePathVariables();
    if (exposePathVariables != null) {
      view.setExposePathVariables(exposePathVariables);
    }
    Boolean exposeContextBeansAsAttributes = getExposeContextBeansAsAttributes();
    if (exposeContextBeansAsAttributes != null) {
      view.setExposeContextBeansAsAttributes(exposeContextBeansAsAttributes);
    }
    String[] exposedContextBeanNames = getExposedContextBeanNames();
    if (exposedContextBeanNames != null) {
      view.setExposedContextBeanNames(exposedContextBeanNames);
    }

    return view;
  }
}
```

📌

- `InternalResourceViewResolver`는 `UrlBasedViewResolver`를 상속받아 구현되어 있습니다.
- `InternalResourceViewResolver`의 `buildView` 메서드에서 View 객체를 생성합니다.
  - `super.buildView(viewName)`를 호출하여 `UrlBasedViewResolver`의 `buildView` 메서드를 호출합니다.
  - `UrlBasedViewResolver`의 `buildView` 메서드의 `view.setUrl(getPrefix() + viewName + getSuffix())`에서 prefix와 suffix를 붙여 viewName을 설정합니다.

- 추가로 `UrlBasedViewResolver`의 `createView` 메서드에서 `viewName`이 `redirect:`로 시작하는 경우 `RedirectView` 객체를 생성합니다.
- `viewName`이 `forward:`로 시작하는 경우 `InternalResourceView` 객체를 생성합니다.
- 그 외의 경우는 `super.createView(viewName, locale)`를 호출하여 `AbstractUrlBasedView` 객체를 생성합니다.

---

## 💬 코멘트

---
