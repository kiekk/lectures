package tobyspring.helloboot;

import java.util.Objects;

public class HelloController {
    // interface 의 구현체가 여러 개일 경우
    // 1. 주입 받을 bean 파라미터명을 bean name 과 동일하게 작성한다.
    private final HelloService helloService;

    public HelloController(HelloService emojiHelloService) {
        this.helloService = emojiHelloService;
    }

    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }

}
