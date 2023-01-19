package tobyspring.helloboot;

import java.util.Objects;

public class HelloController {
    // Spring 4.3 이후 부터는 단일 생성자에 한해서 의존성 생성자 주입 가능
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }

}
