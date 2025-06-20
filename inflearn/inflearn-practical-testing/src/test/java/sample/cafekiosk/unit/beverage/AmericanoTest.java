package sample.cafekiosk.unit.beverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    @DisplayName("아메리카노 이름은 '아메리카노'여야 한다.")
    void getName() {
        Americano americano = new Americano();

        // JUnit API
        assertEquals(americano.getName(), "아메리카노");

        // AssertJ API
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }

    @Test
    @DisplayName("아메리카노 가격은 4,000원이어야 한다.")
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(4_000);
    }
}