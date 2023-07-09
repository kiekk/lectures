package com.example.actuatordemo.tags;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final MeterRegistry meterRegistry;

    public TagController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

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

    @GetMapping("test")
    public String test() {
        Counter counter1 = Counter.builder("my.test")
                .tag("type", "push")
                .register(meterRegistry);

        Counter counter2 = Counter.builder("my.test")
                .tag("type", "push")
                .register(meterRegistry);

        Counter counter3 = Counter.builder("my.test")
                .tag("type", "push2")
                .register(meterRegistry);

        Counter counter4 = Counter.builder("my.test")
                .tag("type", "push3")
                .register(meterRegistry);

        // counter1 과 counter2 는 동일한 객체
        // counter[1,2] 와 counter3, counter4 는 서로 다른 객체
        // tag 가 같은 객체가 있으면 해당 객체를 반환하고 tag 가 다르면 새로운 객체를 생성한다.
        log.info("{} {} {} {}", counter1, counter2, counter3, counter4);

        return "ok";
    }

}
