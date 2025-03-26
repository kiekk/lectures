package com.inflearn.toby.payment;

import java.math.BigDecimal;

public interface ExRateProvider {
    BigDecimal getExRate(String currency);
}
