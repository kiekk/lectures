package com.inflearn.toby;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class PaymentService {
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exRate = getExRate(currency);

        // 금액 계산
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);

        // 유효 시간 계산
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }

    // 환율 정보 가져오기
    abstract BigDecimal getExRate(String currency) throws IOException;
}
