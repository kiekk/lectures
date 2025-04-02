# ☘️ 인증 - Authentication

---

## 📖 내용
- `Authentication`
  - 인증은 특정 자원에 접근하려는 사람의 신원을 확인하는 방법을 의미합니다.
  - `Authentication`은 사용자의 인증 정보를 저장하는 토큰 개념의 객체로 활용되며 인증 이후 `SecurityContext`에 저장되어 전역적으로 참조가 가능합니다.
  - `Authentication` 에 `Principal`은 Spring Security에서는 `UserDetails` 타입을 저장하지만 `Object`로 되어 있어 커스텀 객체를 저장해도 됩니다.  

---

## 🔍 중심 로직

```java
package org.springframework.security.core;

...

public interface Authentication extends Principal, Serializable {
    Collection<? extends GrantedAuthority> getAuthorities();

    Object getCredentials();

    Object getDetails();

    Object getPrincipal();

    boolean isAuthenticated();

    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;
}
```

```java
package java.security;

...

public interface Principal {

    boolean equals(Object another);

    String toString();

    int hashCode();

    String getName();

    default boolean implies(Subject subject) {
        if (subject == null)
            return false;
        return subject.getPrincipals().contains(this);
    }
}
```

```java
package org.springframework.security.core.userdetails;

...

public interface UserDetails extends Serializable {
    Collection<? extends GrantedAuthority> getAuthorities();

    String getPassword();

    String getUsername();

    default boolean isAccountNonExpired() {
        return true;
    }

    default boolean isAccountNonLocked() {
        return true;
    }

    default boolean isCredentialsNonExpired() {
        return true;
    }

    default boolean isEnabled() {
        return true;
    }
}
```

```java
package org.springframework.security.core.userdetails;

...

public class User implements UserDetails, CredentialsContainer {
    private static final long serialVersionUID = 620L;
    private static final Log logger = LogFactory.getLog(User.class);
    private String password;
    private final String username;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        Assert.isTrue(username != null && !"".equals(username) && password != null, "Cannot pass null or empty values to constructor");
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }
    
    ...

    public boolean equals(Object obj) {
        if (obj instanceof User user) {
            // username이 같으면 같은 객체로 판단하도록 equals() 메서드 재정의
            return this.username.equals(user.getUsername());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.username.hashCode();
    }
    
    ...
}
```

📌  요약
- 일반적으로는 `Principal`에 userId와 같은 String아니면 `UserDetails` 타입을 저장하는데, ***만약 커스텀 객체로 저장할 경우에는
반드시 객체에 `equals()`와 `hashCode()` 메서드를 오버라이드 해주어야 합니다.***
- `UserDetails`의 구현체인 `User` 객체를 확인해보면 `equals()`와 `hashCode()` 메서드가 오버라이드 되어 있는 것을 확인할 수 있습니다.

---
