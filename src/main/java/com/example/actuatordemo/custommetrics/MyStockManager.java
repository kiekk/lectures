package com.example.actuatordemo.custommetrics;

import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class MyStockManager implements Supplier<Number> {

    public Long getStockCount() {
        return System.currentTimeMillis();
    }

    @Override
    public Number get() {
        return getStockCount();
    }
}
