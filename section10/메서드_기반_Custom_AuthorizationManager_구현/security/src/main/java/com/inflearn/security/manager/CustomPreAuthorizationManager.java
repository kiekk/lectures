package com.inflearn.security.manager;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

public class CustomPreAuthorizationManager implements AuthorizationManager<MethodInvocation> {
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation object) {
        Authentication auth = authentication.get();

        boolean granted = !isAnonymous(auth) && auth.isAuthenticated();

        return new AuthorizationDecision(granted);
    }

    @Override
    public AuthorizationResult authorize(Supplier<Authentication> authentication, MethodInvocation object) {
        return this.check(authentication, object);
    }

    private static boolean isAnonymous(Authentication auth) {
        return auth instanceof AnonymousAuthenticationToken;
    }
}
