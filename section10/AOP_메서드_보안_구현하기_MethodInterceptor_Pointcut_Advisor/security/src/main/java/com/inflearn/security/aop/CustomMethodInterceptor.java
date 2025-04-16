package com.inflearn.security.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class CustomMethodInterceptor implements MethodInterceptor {

    private final AuthorizationManager<MethodInvocation> authorizationManager;

    public CustomMethodInterceptor(AuthorizationManager<MethodInvocation> authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.requireNonNull(authorizationManager.authorize(() -> authentication, invocation)).isGranted()) {
            return invocation.proceed(); // 실제 대상 객체를 호출한다
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
