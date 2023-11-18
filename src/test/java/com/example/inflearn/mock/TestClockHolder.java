package com.example.inflearn.mock;

import com.example.inflearn.common.service.port.ClockHolder;

public class TestClockHolder implements ClockHolder {

    private final Long millis;

    public TestClockHolder(Long millis) {
        this.millis = millis;
    }

    @Override
    public Long millis() {
        return millis;
    }
}
