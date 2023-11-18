package com.example.inflearn.common.infrastructure;

import com.example.inflearn.common.service.port.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class SystemClockHolder implements ClockHolder {
    @Override
    public Long millis() {
        return Clock.systemUTC().millis();
    }
}
