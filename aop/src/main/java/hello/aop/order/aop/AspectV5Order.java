package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    @Aspect
    @Order(2)
    public static class LogAspect {
        @Around("hello.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            log.info("[log] {}", proceedingJoinPoint.getSignature());   // join point signature

            return proceedingJoinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TxAspect {
        // hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
        @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            try {
                log.info("[트랜잭션 시작] {}", proceedingJoinPoint.getSignature());
                Object result = proceedingJoinPoint.proceed();
                log.info("[트랜잭션 커밋] {}", proceedingJoinPoint.getSignature());
                return result;
            } catch (Exception e) {
                log.info("[트랜잭션 롤백] {}", proceedingJoinPoint.getSignature());
                throw e;
            } finally {
                log.info("[리소스 릴리즈] {}", proceedingJoinPoint.getSignature());
            }
        }
    }

}
