package com.example.actuatordemo.gauge;


import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GaugeConfigWithMeterBinder {

    @Bean
    public MeterBinder gaugeMeterBinder(QueueManager queueManager) {
        return registry -> {
            Gauge.builder("my.queue2.size", queueManager, QueueManager::getQueueSize)
                    .register(registry);
        };
    }

}
