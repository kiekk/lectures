package soono.board.article.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PageLimitCalculatorTest {

    @ParameterizedTest
    @MethodSource("calculatePageLimitTestParameters")
    void calculatePageLimitTest(Long page, Long pageSize, Long movablePageCount, Long expected) {
        Long result = PageLimitCalculator.calculatePageLimit(page, pageSize, movablePageCount);
        assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> calculatePageLimitTestParameters() {
        return Stream.of(
                Arguments.of(1L, 30L, 10L, 301L),
                Arguments.of(7L, 30L, 10L, 301L),
                Arguments.of(10L, 30L, 10L, 301L),
                Arguments.of(11L, 30L, 10L, 601L)
        );
    }
}