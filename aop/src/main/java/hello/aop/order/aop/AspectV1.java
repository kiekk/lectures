package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {

    @Around("execution (* hello.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("[log] {}", proceedingJoinPoint.getSignature());   // join point signature

        return proceedingJoinPoint.proceed();
    }

}
