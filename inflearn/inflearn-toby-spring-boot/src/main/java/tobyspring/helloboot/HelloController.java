package tobyspring.helloboot;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello(String name) {
        if(Strings.isEmpty(name)) throw new IllegalArgumentException();
        return helloService.sayHello(name);
    }

    @GetMapping("count")
    public String count(String name) {
        return name + ": " + helloService.countOf(name);
    }

}
