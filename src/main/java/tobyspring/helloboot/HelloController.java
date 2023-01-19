package tobyspring.helloboot;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

// 토비님 강의에서는 2.X 버전이기 때문에 @Component + @RequestMapping 조합으로도 MVC 에서 Controller 로 인식합니다.
// 하지만 Spring Boot 3, Spring 6 부터는 더 이상 이 방식이 적용되지 않습니다.
// 토비님 강의에서 사용하는 브랜치는 main 이고,
// 또 다른 브랜치인 springboot3 브랜치에는 소스가 변경되어 있습니다.
// 관련해서는 README.md 에도 정리해두었습니다.
//@Component
@Controller
@RequestMapping("/hello")
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService emojiHelloService) {
        this.helloService = emojiHelloService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }

}
