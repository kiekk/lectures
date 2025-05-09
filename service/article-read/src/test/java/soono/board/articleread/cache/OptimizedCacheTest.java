package soono.board.articleread.cache;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class OptimizedCacheTest {

    @ParameterizedTest
    @MethodSource("parseDataParameters")
    void parseDataTest(Object data, long ttlSeconds) {
        // given
        OptimizedCache optimizedCache = OptimizedCache.of(data, Duration.ofSeconds(ttlSeconds));
        log.info("OptimizedCache: {}", optimizedCache);

        // when
        Object resolveData = optimizedCache.parseData(data.getClass());

        // then
        log.info("resolveData: {}", resolveData);
        assertThat(resolveData).isEqualTo(data);
    }

    static Stream<Arguments> parseDataParameters() {
        return Stream.of(
                Arguments.of("data", 10),
                Arguments.of(123, 10),
                Arguments.of(123.456, 10),
                Arguments.of(true, 10),
                Arguments.of(new TestClass("data"), 10)
        );
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestClass {
        String testData;
    }

    @ParameterizedTest
    @MethodSource("expiredParameters")
    void isExpiredTest(Object data, long ttlSeconds, boolean expected) {
        // given
        OptimizedCache optimizedCache = OptimizedCache.of(data, Duration.ofSeconds(ttlSeconds));
        log.info("OptimizedCache: {}", optimizedCache);

        // when
        boolean isExpired = optimizedCache.isExpired();

        // then
        log.info("isExpired: {}", isExpired);
        assertThat(isExpired).isEqualTo(expected);
    }

    static Stream<Arguments> expiredParameters() {
        return Stream.of(
                Arguments.of("data", -30, true),
                Arguments.of("data", -1, true),
                Arguments.of("data", 1, false),
                Arguments.of("data", 10, false)
        );
    }

}