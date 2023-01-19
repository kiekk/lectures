package tobyspring.helloboot;

import java.util.Objects;

public class HelloController {
    // Spring 4.3 이후 부터는 단일 생성자에 한해서 의존성 생성자 주입 가능
    private final SimpleHelloService simpleHelloService;

    public HelloController(SimpleHelloService simpleHelloService) {
        this.simpleHelloService = simpleHelloService;
    }

    public String hello(String name) {
        return simpleHelloService.sayHello(Objects.requireNonNull(name));
    }

}
