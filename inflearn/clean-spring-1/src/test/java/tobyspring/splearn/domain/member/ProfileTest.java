package tobyspring.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {
    @Test
    void profile() {
        assertDoesNotThrow(() -> new Profile("test"));
    }

    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile(""))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("testteteststetset"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("A"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("한글"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        var profile = new Profile("soooo");

        assertThat(profile.url()).isEqualTo("@soooo");
    }
}