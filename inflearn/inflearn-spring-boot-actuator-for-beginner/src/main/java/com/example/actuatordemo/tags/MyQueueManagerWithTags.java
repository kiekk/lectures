package com.example.actuatordemo.tags;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MyQueueManagerWithTags {

    private final MeterRegistry meterRegistry;

    public MyQueueManagerWithTags(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void push() {
        Counter.builder("my.queue.counter")
                .tag("type", "push")
                .register(meterRegistry)
                .increment();
    }

    public void pop() {
        Counter.builder("my.queue.counter")
                .tag("type", "pop")
                .register(meterRegistry)
                .increment();
    }

}
