package com.inflearn.toby.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class Payment {
    private final Long orderId;
    private final String currency;
    // Tip: Double 타입을 사용할 경우 부동소수점으로 인해 오차가 발생할 수 있음
    private final BigDecimal foreignCurrencyAmount;
    private final BigDecimal exRate;
    private final BigDecimal convertedAmount;
    private final LocalDateTime validUntil;

    private Payment(Long orderId, String currency, BigDecimal foreignCurrencyAmount, BigDecimal exRate,
                   BigDecimal convertedAmount, LocalDateTime validUntil) {
        this.orderId = orderId;
        this.currency = currency;
        this.foreignCurrencyAmount = foreignCurrencyAmount;
        this.exRate = exRate;
        this.convertedAmount = convertedAmount;
        this.validUntil = validUntil;
    }

    public static Payment createPrepared(Long orderId, String currency, BigDecimal foreignCurrencyAmount, BigDecimal exRate, LocalDateTime now) {
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = now.plusMinutes(30);
        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }

    public boolean isValid(Clock clock) {
        return LocalDateTime.now(clock).isBefore(validUntil);
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getForeignCurrencyAmount() {
        return foreignCurrencyAmount;
    }

    public BigDecimal getExRate() {
        return exRate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "orderId=" + orderId +
                ", currency='" + currency + '\'' +
                ", foreignCurrencyAmount=" + foreignCurrencyAmount +
                ", exRate=" + exRate +
                ", convertedAmount=" + convertedAmount +
                ", validUntil=" + validUntil +
                '}';
    }
}
