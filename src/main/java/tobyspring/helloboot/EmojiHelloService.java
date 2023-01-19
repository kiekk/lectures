package tobyspring.helloboot;

public class EmojiHelloService implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " 😜";
    }
}
