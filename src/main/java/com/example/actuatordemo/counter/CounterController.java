package com.example.actuatordemo.counter;

import io.micrometer.core.annotation.Counted;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("counter")
public class CounterController {

    @Counted("my.counted.counter")
    @GetMapping("req")
    public String req() {
        return "ok";
    }

}
