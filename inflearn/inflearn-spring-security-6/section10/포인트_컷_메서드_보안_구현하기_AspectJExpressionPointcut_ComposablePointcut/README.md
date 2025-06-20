# ☘️ 포인트 컷 메서드 보안 구현하기 - AspectJExpressionPointcut / ComposablePointcut

---

## 📖 내용
- 메서드 보안을 애노테이션 방식이 아닌 패턴 형태로 선언할 수 있으며 자체 어드바이저 또는 포인트컷을 사용하여 메서드 보안을 구현할 수 있습니다.
- 단일 포인트컷을 사용할 경우 `AspectJExpressionPointcut`을 사용하고, 여러 포인트컷을 사용할 경우 `ComposablePointcut`을 통해 여러 `AspectJExpressionPointcut`을 조합합니다.

---

## 🔍 중심 로직

```java
package org.springframework.aop.aspectj;

...

public class AspectJExpressionPointcut extends AbstractExpressionPointcut implements ClassFilter, IntroductionAwareMethodMatcher, BeanFactoryAware {
    private static final String AJC_MAGIC = "ajc$";
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES;
    private static final Log logger;
    @Nullable
    private Class<?> pointcutDeclarationScope;
    private boolean aspectCompiledByAjc;
    private String[] pointcutParameterNames = new String[0];
    private Class<?>[] pointcutParameterTypes = new Class[0];
    @Nullable
    private BeanFactory beanFactory;
    @Nullable
    private transient ClassLoader pointcutClassLoader;
    @Nullable
    private transient PointcutExpression pointcutExpression;
    private transient boolean pointcutParsingFailed = false;

    public AspectJExpressionPointcut() {
    }

    public AspectJExpressionPointcut(Class<?> declarationScope, String[] paramNames, Class<?>[] paramTypes) {
        this.setPointcutDeclarationScope(declarationScope);
        if (paramNames.length != paramTypes.length) {
            throw new IllegalStateException("Number of pointcut parameter names must match number of pointcut parameter types");
        } else {
            this.pointcutParameterNames = paramNames;
            this.pointcutParameterTypes = paramTypes;
        }
    }
    
    ... other methods

    public boolean matches(Class<?> targetClass) {
        if (this.pointcutParsingFailed) {
            return false;
        } else if (this.aspectCompiledByAjc && compiledByAjc(targetClass)) {
            return false;
        } else {
            try {
                try {
                    return this.obtainPointcutExpression().couldMatchJoinPointsInType(targetClass);
                } catch (ReflectionWorld.ReflectionWorldException ex) {
                    logger.debug("PointcutExpression matching rejected target class - trying fallback expression", ex);
                    PointcutExpression fallbackExpression = this.getFallbackPointcutExpression(targetClass);
                    if (fallbackExpression != null) {
                        return fallbackExpression.couldMatchJoinPointsInType(targetClass);
                    }
                }
            } catch (IllegalStateException | UnsupportedPointcutPrimitiveException | IllegalArgumentException ex) {
                this.pointcutParsingFailed = true;
                if (logger.isDebugEnabled()) {
                    Log var10000 = logger;
                    String var10001 = this.getExpression();
                    var10000.debug("Pointcut parser rejected expression [" + var10001 + "]: " + String.valueOf(ex));
                }
            } catch (Throwable ex) {
                logger.debug("PointcutExpression matching rejected target class", ex);
            }

            return false;
        }
    }

    public boolean matches(Method method, Class<?> targetClass, boolean hasIntroductions) {
        ShadowMatch shadowMatch = this.getTargetShadowMatch(method, targetClass);
        if (shadowMatch.alwaysMatches()) {
            return true;
        } else if (shadowMatch.neverMatches()) {
            return false;
        } else if (hasIntroductions) {
            return true;
        } else {
            RuntimeTestWalker walker = this.getRuntimeTestWalker(shadowMatch);
            return !walker.testsSubtypeSensitiveVars() || walker.testTargetInstanceOfResidue(targetClass);
        }
    }

    public boolean matches(Method method, Class<?> targetClass) {
        return this.matches(method, targetClass, false);
    }

    public boolean isRuntime() {
        return this.obtainPointcutExpression().mayNeedDynamicTest();
    }

    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        ShadowMatch shadowMatch = this.getTargetShadowMatch(method, targetClass);
        ProxyMethodInvocation pmi = null;
        Object targetObject = null;
        Object thisObject = null;

        try {
            MethodInvocation curr = ExposeInvocationInterceptor.currentInvocation();
            if (curr.getMethod() == method) {
                targetObject = curr.getThis();
                if (!(curr instanceof ProxyMethodInvocation)) {
                    throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + String.valueOf(curr));
                }

                ProxyMethodInvocation currPmi = (ProxyMethodInvocation)curr;
                pmi = currPmi;
                thisObject = currPmi.getProxy();
            }
        } catch (IllegalStateException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not access current invocation - matching with limited context: " + String.valueOf(ex));
            }
        }

        try {
            JoinPointMatch joinPointMatch = shadowMatch.matchesJoinPoint(thisObject, targetObject, args);
            if (pmi != null && thisObject != null) {
                RuntimeTestWalker originalMethodResidueTest = this.getRuntimeTestWalker(this.getShadowMatch(method, method));
                if (!originalMethodResidueTest.testThisInstanceOfResidue(thisObject.getClass())) {
                    return false;
                }

                if (joinPointMatch.matches()) {
                    this.bindParameters(pmi, joinPointMatch);
                }
            }

            return joinPointMatch.matches();
        } catch (Throwable ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("Failed to evaluate join point for arguments " + Arrays.toString(args) + " - falling back to non-match", ex);
            }

            return false;
        }
    }

    ... other methods
```

```java
package org.springframework.aop.support;

...

public class ComposablePointcut implements Pointcut, Serializable {
    private static final long serialVersionUID = -2743223737633663832L;
    private ClassFilter classFilter;
    private MethodMatcher methodMatcher;

    public ComposablePointcut() {
        this.classFilter = ClassFilter.TRUE;
        this.methodMatcher = MethodMatcher.TRUE;
    }

    public ComposablePointcut(Pointcut pointcut) {
        Assert.notNull(pointcut, "Pointcut must not be null");
        this.classFilter = pointcut.getClassFilter();
        this.methodMatcher = pointcut.getMethodMatcher();
    }

    public ComposablePointcut(ClassFilter classFilter) {
        Assert.notNull(classFilter, "ClassFilter must not be null");
        this.classFilter = classFilter;
        this.methodMatcher = MethodMatcher.TRUE;
    }

    public ComposablePointcut(MethodMatcher methodMatcher) {
        Assert.notNull(methodMatcher, "MethodMatcher must not be null");
        this.classFilter = ClassFilter.TRUE;
        this.methodMatcher = methodMatcher;
    }

    public ComposablePointcut(ClassFilter classFilter, MethodMatcher methodMatcher) {
        Assert.notNull(classFilter, "ClassFilter must not be null");
        Assert.notNull(methodMatcher, "MethodMatcher must not be null");
        this.classFilter = classFilter;
        this.methodMatcher = methodMatcher;
    }

    public ComposablePointcut union(ClassFilter other) {
        this.classFilter = ClassFilters.union(this.classFilter, other);
        return this;
    }

    public ComposablePointcut intersection(ClassFilter other) {
        this.classFilter = ClassFilters.intersection(this.classFilter, other);
        return this;
    }

    public ComposablePointcut union(MethodMatcher other) {
        this.methodMatcher = MethodMatchers.union(this.methodMatcher, other);
        return this;
    }

    public ComposablePointcut intersection(MethodMatcher other) {
        this.methodMatcher = MethodMatchers.intersection(this.methodMatcher, other);
        return this;
    }

    public ComposablePointcut union(Pointcut other) {
        this.methodMatcher = MethodMatchers.union(this.methodMatcher, this.classFilter, other.getMethodMatcher(), other.getClassFilter());
        this.classFilter = ClassFilters.union(this.classFilter, other.getClassFilter());
        return this;
    }

    public ComposablePointcut intersection(Pointcut other) {
        this.classFilter = ClassFilters.intersection(this.classFilter, other.getClassFilter());
        this.methodMatcher = MethodMatchers.intersection(this.methodMatcher, other.getMethodMatcher());
        return this;
    }

    ... other methods
}
```


### 단일 포인트컷
```java
@Bean
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public Advisor protectServicePointcut() {
    AspectJExpressionPointcut pattern = new AspectJExpressionPointcut();
    pattern.setExpression("execution(* io.security.MyService.user(..))");
    manager = AuthorityAuthorizationManager.hasRole("USER");
    return new AuthorizationManagerBeforeMethodInterceptor(pattern, manager);
}
```

### 다중 포인트컷
```java
@Bean
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public Advisor protectServicePointcut() {
    return new AuthorizationManagerBeforeMethodInterceptor(createCompositePointcut(), hasRole("USER"));
}

public Pointcut createCompositePointcut() {
    AspectJExpressionPointcut pointcut1 = new AspectJExpressionPointcut();
    pointcut1.setExpression("execution(* io.security.MyService.user(..))");
    AspectJExpressionPointcut pointcut2 = new AspectJExpressionPointcut();
    pointcut2.setExpression("execution(* io.security.MyService.display(..))");
    // 두 포인트컷을 조합
    ComposablePointcut compositePointcut = new ComposablePointcut(pointcut1);
    compositePointcut.union(pointcut2);
    return compositePointcut;
}
```

📌

---

## 📂 참고할만한 자료

