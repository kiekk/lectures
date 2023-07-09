package com.example.actuatordemo.tags;

import io.micrometer.core.annotation.Counted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Counted("my.counted")
    @GetMapping("push")
    public String push() {
        return "ok";
    }

    @Counted("my.counted")
    @GetMapping("pop")
    public String pop() {
        return "ok";
    }

}
