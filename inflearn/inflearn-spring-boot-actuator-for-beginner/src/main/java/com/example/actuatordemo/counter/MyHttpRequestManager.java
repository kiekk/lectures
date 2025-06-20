package com.example.actuatordemo.counter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class MyHttpRequestManager {

    private final MeterRegistry meterRegistry;
    private Counter httpRequestCounter;

    public MyHttpRequestManager(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    void init() {
        httpRequestCounter = Counter.builder("my.http.request")
                .register(meterRegistry);
    }

    public void increase() {
        httpRequestCounter.increment();
    }

}
