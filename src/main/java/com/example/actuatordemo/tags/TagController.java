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

    @Counted(value = "my.counted", extraTags = {"type1", "value1", "type2", "value2"})
    @GetMapping("push")
    public String push() {
        return "ok";
    }

    @Counted(value = "my.counted", extraTags = {"type3", "value3", "type4", "value4"})
    @GetMapping("pop")
    public String pop() {
        return "ok";
    }

}
