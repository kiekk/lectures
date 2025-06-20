package com.inflearn.security.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;

@Configuration
public class AopConfig {
    @Bean
    public MethodInterceptor customMethodInterceptor() {
        AuthorizationManager<MethodInvocation> authorizationManager = AuthenticatedAuthorizationManager.authenticated();
        return new CustomMethodInterceptor(authorizationManager); // AOP 어라운드 어드바이스를 선언한다
    }

    @Bean
    public Pointcut servicePointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* io.security.Myservice.*(..))"); // AOP 수행 대상 클래스와 대상 메소드를 지정한다
        return pointcut;
    }

    @Bean
    public Advisor serviceAdvisor(MethodInterceptor customMethodInterceptor, Pointcut servicePointcut) { // 초기화 시 Advisor 목록에 포함된다
        return new DefaultPointcutAdvisor(servicePointcut, customMethodInterceptor);
    }
}
