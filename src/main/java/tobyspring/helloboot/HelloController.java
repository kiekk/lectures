package tobyspring.helloboot;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService emojiHelloService) {
        this.helloService = emojiHelloService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }

}
