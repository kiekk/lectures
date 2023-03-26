# test-spring-bean-stateless

### stateless
```
Spring에서 Bean 설계 시 항상 무상태(stateless)로 설계하라고 합니다.
이 말을 이해하려면 먼저 Spring에서 Bean의 Scope에 대해 알아야 합니다.

Scope는 간단히 말해 Bean의 적용 범위, 생명 주기라고 볼 수 있습니다.
```

### scope의 종류
```
scope는 아래와 같이 5개의 종류로 구분됩니다.

singleton: 하나의 Bean 정의에 대해서 Spring IoC Container 내에 단 하나의 객체만 존재합니다.
prototype: 하나의 Bean 정의에 대해서 다수의 객체가 존재할 수 있습니다.
request: 하나의 Bean 정의에 대해서 Http Request 생명 주기 안에 단 하나의 객체만 존재합니다.
session: 하나의 Bean 정의에 대해서 Http Session 생명 주기 안에 단 하나의 객체만 존재합니다.
global session: 하나의 Bean 정의에 대해서 global Http Session 생명 주기 안에 단 하나의 객체만 존재합니다.
```

```
우리가 살펴볼 것은 singleton과 prototype입니다.
먼저 Spring에서는 기본적으로 Bean의 scope를 singletone으로 설정해두고 있습니다.

기본적으로 Bean을 등록하게 되면 여러 사용자가 같은 객체를 사용하게 됩니다.
그렇기 때문에 앞서 설명했던 것처럼 Bean을 항상 무상태(stateless)로 설계하라고 하는 것입니다.

Bean이 상태를 가진다면(stateful) 여러 사용자가 공유할 수 있는 전역 변수 또는 전역 공간이 생긴다고 봐도 무방합니다.
공유할 수 있는 전역 변수, 전역 공간은 항상 '동시성'이라는 이슈가 뒤따라옵니다.

이를 확인하기 위해 작성했던 statefulServiceSingleton 테스트를 확인해보면 알 수 있습니다.
하지만 그렇다고 Bean이 상태를 가질 수 없는 것은 아닙니다.
statefulServicePrototype 테스트처럼 Bean의 scope를 prototype으로 설정하게 되면
Bean이 상태를 가진다고 해도 '동시성' 이슈가 발생하지 않습니다.

대신, Bean을 요청할 때마다 매번 새로운 객체가 생성된다는 단점이 존재합니다.

그래서 Spring에서는 기본적으로 Bean의 scope를 singleton으로 설정한 후 무상태(stateless)로 설계하라고 하는 것입니다.

무상태로 설계하는 방법은 statelessService 테스트에서 확인할 수 있습니다.
Bean이 상태를 가지는 것이 아니라 상태를 반환함으로써, 반환 받은 곳에서 해당 상태를 저장하는 것입니다.

이렇게 하면 Bean은 상태를 가지지 않게 됨으로 singleton으로 사용할 수 있으며,
Bean을 사용하는 사용자 또한 자신의 상태 값을 반환받기 때문에 '동시성' 이슈도 발생하지 않게 됩니다. 
```