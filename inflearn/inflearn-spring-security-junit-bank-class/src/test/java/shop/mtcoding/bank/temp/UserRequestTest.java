package shop.mtcoding.bank.temp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UserRequestTest {

    @DisplayName("username 은 영문,숫자만 되며 2~20자여야 한다.")
    @ParameterizedTest
    @MethodSource("usernameSource")
    void user_username_test(String username, boolean expected) {
        assertThat(Pattern.matches("^[a-zA-Z0-9]{2,20}$", username)).isEqualTo(expected);
    }

    @DisplayName("fullname 은 영문,한글만 되며 1~20자여야 한다.")
    @ParameterizedTest
    @MethodSource("fullnameSource")
    void user_fullname_test(String fullname, boolean expected) {
        assertThat(Pattern.matches("^[a-zA-Z가-힣]{1,20}$", fullname)).isEqualTo(expected);
    }

    @DisplayName("email 은 아이디는 영문, 숫자만 되며 2~6자여야 한다. 그리고 이메일 형식이어야 한다.")
    @ParameterizedTest
    @MethodSource("emailSource")
    void user_email_test(String email, boolean expected) {
        assertThat(Pattern.matches("^[a-zA-Z0-9]{2,6}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", email)).isEqualTo(expected);
    }

    static Stream<Arguments> usernameSource() {
        return Stream.of(
                arguments("", false),
                arguments("a", false),
                arguments("가", false),
                arguments("a가ad", false),
                arguments("aaaaaaaaaaaaaaaaaaaaaa", false),
                arguments("aa", true),
                arguments("22", true),
                arguments("aadd22", true)
        );
    }

    static Stream<Arguments> fullnameSource() {
        return Stream.of(
                arguments("", false),
                arguments("22", false),
                arguments("aadd22", false),
                arguments("aaaaaaaaaaaaaaaaaaaaaa", false),
                arguments("abcdefghijk가나다라마바사아자차카타파하", false),
                arguments("ㄱ", false), // 자음은 안된다
                arguments("ㅏ", false), // 모음은 안된다
                arguments("a가ad", true),
                arguments("a", true),
                arguments("가", true),
                arguments("aa", true)
        );
    }

    static Stream<Arguments> emailSource() {
        return Stream.of(
                arguments("", false),
                arguments("ㄱ", false), // 자음은 안된다
                arguments("ㅏ", false), // 모음은 안된다
                arguments("aaa111", false), // 이메일 형식이 아니다
                arguments("aaa111@", false), // 이메일 형식이 아니다
                arguments("aa@ggggdeieir.com", false),
                arguments("aa@gmail.cocc", false),
                arguments("abcdefghijk가나다라마바사아자차카타파하", false),
                arguments("aa@gmail.com", true),
                arguments("aa111@gmail.com", true)
        );
    }
}
