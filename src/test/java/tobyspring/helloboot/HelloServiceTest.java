package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloServiceTest {
    @Test
    void emojiHelloServiceTest() {
        EmojiHelloService emojiHelloService = new EmojiHelloService(helloRepositoryStub);

        String result = emojiHelloService.sayHello("Spring");

        Assertions.assertThat(result).isEqualTo("Hello, Spring \uD83D\uDE1C");
    }

    @Test
    void simpleHelloServiceTest() {
        SimpleHelloService simpleHelloService = new SimpleHelloService(helloRepositoryStub);

        String result = simpleHelloService.sayHello("Spring");

        Assertions.assertThat(result).isEqualTo("Hello Spring");
    }

    /*
    Test 에서 별다른 기능을 하지 않지만,
    객체 구조에 의해 어쩔수 없이 의존성을 추가해야 하는 경우에는,
    아래와 같이 테스트용 객체를 만들어 주입하는 방법도 있다.
     */
    private static HelloRepository helloRepositoryStub = new HelloRepository() {
        @Override
        public Hello findHello(String name) {
            return null;
        }

        @Override
        public void increaseCount(String name) {

        }
    };

    @Test
    void helloDecorator() {
        HelloDecorator decorator = new HelloDecorator(name -> name);

        String result = decorator.sayHello("Test");

        Assertions.assertThat(result).isEqualTo("**Test**");
    }
}
