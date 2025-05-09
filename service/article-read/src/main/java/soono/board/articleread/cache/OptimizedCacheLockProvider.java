package soono.board.articleread.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OptimizedCacheLockProvider {
    private final StringRedisTemplate redisTemplate;

    private static final String KEY_FORMAT = "optimized-cache-lock::";
    private static final Duration LOCK_TTL = Duration.ofSeconds(3);

    public boolean lock(String key) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(
                generateLockKey(key),
                "",
                LOCK_TTL
        ));
    }

    public boolean unlock(String key) {
        return redisTemplate.delete(generateLockKey(key));
    }

    private String generateLockKey(String key) {
        return KEY_FORMAT + key;
    }
}
