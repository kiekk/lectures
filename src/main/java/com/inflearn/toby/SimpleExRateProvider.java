package com.inflearn.toby;

import java.math.BigDecimal;

public class SimpleExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExRate(String currency) {
        if ("USD".equals(currency)) {
            return BigDecimal.valueOf(1000);
        }
        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
