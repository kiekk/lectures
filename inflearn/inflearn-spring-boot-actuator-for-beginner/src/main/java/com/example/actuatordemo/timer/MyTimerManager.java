package com.example.actuatordemo.timer;

import org.springframework.stereotype.Service;

@Service
public class MyTimerManager {

    public Long getCount() {
        return System.currentTimeMillis();
    }

    public Long getTotalTime() {
        return System.currentTimeMillis();
    }

}
