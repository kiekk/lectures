package com.example.actuatordemo.gauge;


import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GaugeConfig {

    private final QueueManager queueManager;
    private final MeterRegistry meterRegistry;

    public GaugeConfig(QueueManager queueManager, MeterRegistry meterRegistry) {
        this.queueManager = queueManager;
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void register() {
        Gauge.builder("my.queue.size", queueManager, QueueManager::getQueueSize)
                .register(meterRegistry);
    }

}
