package soono.board.articleread.learning;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class LongToDoubleTest {

    @Test
    void longToDoubleTest() {
        long longValue = 111_111_111_111_111_111L;
        System.out.println("longValue = " + longValue);

        double doubleValue = (double) longValue;
        System.out.println("doubleValue = " + doubleValue);
        System.out.println("doubleValue bigDecimal = " + new BigDecimal(doubleValue));

        long longValue2 = (long) doubleValue;
        System.out.println("longValue2 = " + longValue2);

        assertThat(longValue).isNotEqualTo(longValue2);
        // double로 강제 형변환하면서 값이 변경됨
        /*
        long은 64비트 정수
        double은 64비트 부동소수점
         */
    }
}
