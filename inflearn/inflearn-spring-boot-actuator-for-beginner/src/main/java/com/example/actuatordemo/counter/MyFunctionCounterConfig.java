package com.example.actuatordemo.counter;

import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFunctionCounterConfig {

    private final MyHttpRequestWithoutMicrometer myManager;
    private final MeterRegistry registry;

    public MyFunctionCounterConfig(MyHttpRequestWithoutMicrometer myManager, MeterRegistry registry) {
        this.myManager = myManager;
        this.registry = registry;
    }

    @PostConstruct
    void init() {
        FunctionCounter.builder("my.function.counter", myManager, MyHttpRequestWithoutMicrometer::getCount).register(registry);
    }

}
