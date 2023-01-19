package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloControllerTest {

    @Test
    void helloControllerSuccessTest() {
        HelloController helloController = new HelloController(name -> name);

        String result = helloController.hello("Spring");

        Assertions.assertThat(result).isEqualTo("Spring");
    }

    @Test
    void helloControllerFailTest() {
        HelloController helloController = new HelloController(name -> name);

        Assertions.assertThatThrownBy(() -> {
            helloController.hello(null);
        }).isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> {
            helloController.hello("");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
