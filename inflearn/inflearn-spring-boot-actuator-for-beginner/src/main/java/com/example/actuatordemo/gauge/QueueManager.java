package com.example.actuatordemo.gauge;

import org.springframework.stereotype.Service;

@Service
public class QueueManager {

    public Long getQueueSize() {
        return System.currentTimeMillis();
    }
}
