package com.inflearn.security.manager;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.method.MethodInvocationResult;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

public class CustomPostAuthorizationManager implements AuthorizationManager<MethodInvocationResult> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocationResult object) {
        Authentication auth = authentication.get();

        boolean granted = !isAnonymous(auth) && auth.isAuthenticated();

        // result 검증
        Object result = object.getResult();
        // granted = ...

        return new AuthorizationDecision(granted);
    }

    @Override
    public AuthorizationResult authorize(Supplier<Authentication> authentication, MethodInvocationResult object) {
        return this.check(authentication, object);
    }

    private static boolean isAnonymous(Authentication auth) {
        return auth instanceof AnonymousAuthenticationToken;
    }
}
