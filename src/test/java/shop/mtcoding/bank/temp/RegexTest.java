package shop.mtcoding.bank.temp;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

// java.util.regex.Pattern
public class RegexTest {

    @Test
    void 한글만된다_test() {
        String value1 = "가";
        String value2 = "";
        String value3 = "ㄴ";
        assertThat(Pattern.matches("^[가-힣]+$", value1)).isTrue();
        assertThat(Pattern.matches("^[가-힣]+$", value2)).isFalse();
        assertThat(Pattern.matches("^[가-힣]+$", value3)).isFalse();
    }

    @Test
    void 한글은안된다_test() {
        String value1 = "가";
        String value2 = "";
        String value3 = "ㄴ";
        assertThat(Pattern.matches("^[^ㄱ-ㅎ가-힣]+$", value1)).isFalse();
        assertThat(Pattern.matches("^[^ㄱ-ㅎ가-힣]+$", value2)).isFalse();
        assertThat(Pattern.matches("^[^ㄱ-ㅎ가-힣]+$", value3)).isFalse();
    }

    @Test
    void 영어만된다_test() {
        String value1 = "A";
        String value2 = "";
        String value3 = "a";
        assertThat(Pattern.matches("^[a-zA-Z]+$", value1)).isTrue();
        assertThat(Pattern.matches("^[a-zA-Z]+$", value2)).isFalse();
        assertThat(Pattern.matches("^[a-zA-Z]+$", value3)).isTrue();
    }

    @Test
    void 영어는안된다_test() {
        String value1 = "A";
        String value2 = "";
        String value3 = "a";
        assertThat(Pattern.matches("^[^a-zA-Z]+$", value1)).isFalse();
        assertThat(Pattern.matches("^[^a-zA-Z]+$", value2)).isFalse();
        assertThat(Pattern.matches("^[^a-zA-Z]+$", value3)).isFalse();
    }

    @Test
    void 영어와숫자만된다_test() {
        String value1 = "A";
        String value2 = "";
        String value3 = "a";
        String value4 = "1";
        assertThat(Pattern.matches("^[a-zA-Z0-9]+$", value1)).isTrue();
        assertThat(Pattern.matches("^[a-zA-Z0-9]+$", value2)).isFalse();
        assertThat(Pattern.matches("^[a-zA-Z0-9]+$", value3)).isTrue();
        assertThat(Pattern.matches("^[a-zA-Z0-9]+$", value4)).isTrue();
    }

    @Test
    void 영어만되고_길이는최소2최대4이다_test() {
        String value1 = "A";
        String value2 = "";
        String value3 = "aa";
        String value4 = "aaaa";
        String value5 = "aaaaa";
        assertThat(Pattern.matches("^[a-zA-Z]{2,4}$", value1)).isFalse();
        assertThat(Pattern.matches("^[a-zA-Z]{2,4}$", value2)).isFalse();
        assertThat(Pattern.matches("^[a-zA-Z]{2,4}$", value3)).isTrue();
        assertThat(Pattern.matches("^[a-zA-Z]{2,4}$", value4)).isTrue();
        assertThat(Pattern.matches("^[a-zA-Z]{2,4}$", value5)).isFalse();
    }

}
