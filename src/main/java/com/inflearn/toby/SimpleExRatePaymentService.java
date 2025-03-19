package com.inflearn.toby;

import java.io.IOException;
import java.math.BigDecimal;

public class SimpleExRatePaymentService extends PaymentService {
    @Override
    BigDecimal getExRate(String currency) {
        if ("USD".equals(currency)) {
            return BigDecimal.valueOf(1000);
        }
        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
