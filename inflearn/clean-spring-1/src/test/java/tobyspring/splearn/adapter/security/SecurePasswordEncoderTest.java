package tobyspring.splearn.adapter.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SecurePasswordEncoderTest {

    @Test
    void securePasswordEncoderTest() {
        var securePasswordEncoder = new SecurePasswordEncoder();

        var passwordHash = securePasswordEncoder.encode("secret");

        assertThat(securePasswordEncoder.matches("secret", passwordHash)).isTrue();
        assertThat(securePasswordEncoder.matches("isWrong", passwordHash)).isFalse();
    }

}