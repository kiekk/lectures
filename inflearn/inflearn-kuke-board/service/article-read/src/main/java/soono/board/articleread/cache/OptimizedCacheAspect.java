package soono.board.articleread.cache;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OptimizedCacheAspect {
    private final OptimizedCacheManager optimizedCacheManager;

    @Around("@annotation(OptimizedCacheable)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        OptimizedCacheable cacheable = findAnnotation(joinPoint);
        return optimizedCacheManager.process(
                cacheable.type(),
                cacheable.ttlSeconds(),
                joinPoint.getArgs(),
                findReturnType(joinPoint),
                () -> {
                    try {
                        return joinPoint.proceed();
                    } catch (Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }
                }
        );
    }

    private OptimizedCacheable findAnnotation(ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(OptimizedCacheable.class);
    }

    private Class<?> findReturnType(ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getReturnType();
    }
}
