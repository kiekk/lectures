package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloServiceTest {
    @Test
    void emojiHelloServiceTest() {
        EmojiHelloService emojiHelloService = new EmojiHelloService();

        String result = emojiHelloService.sayHello("Spring");

        Assertions.assertThat(result).isEqualTo("Hello, Spring \uD83D\uDE1C");
    }

    @Test
    void simpleHelloServiceTest() {
        SimpleHelloService simpleHelloService = new SimpleHelloService();

        String result = simpleHelloService.sayHello("Spring");

        Assertions.assertThat(result).isEqualTo("Hello Spring");
    }

    @Test
    void helloDecorator() {
        HelloDecorator decorator = new HelloDecorator(name -> name);

        String result = decorator.sayHello("Test");

        Assertions.assertThat(result).isEqualTo("**Test**");
    }
}
