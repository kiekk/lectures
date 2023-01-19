package tobyspring.helloboot;

import org.springframework.stereotype.Service;

@Service
public class EmojiHelloService implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " 😜";
    }
}
