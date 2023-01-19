package tobyspring.helloboot;

import org.springframework.stereotype.Component;

@Component
public class EmojiHelloService implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " 😜";
    }
}
