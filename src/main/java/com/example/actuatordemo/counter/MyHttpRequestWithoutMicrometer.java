package com.example.actuatordemo.counter;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class MyHttpRequestWithoutMicrometer {

    private AtomicLong count = new AtomicLong(0);

    public Long getCount() {
        return count.get();
    }

    public void increase() {
        count.incrementAndGet();
    }
}
