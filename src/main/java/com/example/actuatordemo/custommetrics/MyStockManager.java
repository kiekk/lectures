package com.example.actuatordemo.custommetrics;

import org.springframework.stereotype.Component;

@Component
public class MyStockManager {

    public Long getStockCount() {
        return System.currentTimeMillis();
    }
}
