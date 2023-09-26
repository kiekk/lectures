package shop.mtcoding.bank.temp;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LongTest {

    @Test
    void longTest() {
        // given
        Long number1 = 1111L;
        Long number2 = 1111L;

        // then
        assertThat(number1 == number2).isFalse();
        assertThat(number1.equals(number2)).isTrue();
        assertThat(number1.longValue() == number2.longValue()).isTrue();
    }
}
