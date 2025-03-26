package com.inflearn.toby.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceTest {

    private Clock clock;

    @BeforeEach
    void beforeEach() {
        clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    @DisplayName("prepare 메서드의 요구사항 3가지를 잘 충족했는지 검증, 1.적용환율 2.원화 환산 금액 3.원화 환산 금액 유효시간")
    void convertedAmount() throws IOException {
        testAmount(valueOf(500), valueOf(5_000), clock);
        testAmount(valueOf(1_00), valueOf(10_000), clock);
        testAmount(valueOf(3_00), valueOf(30_000), clock);
    }

    @Test
    @DisplayName("Payment.validUntil은 현재 시간으로부터 30분 이후의 시간이어야 한다.")
    void validUntil() throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1_000)), clock);
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.TEN);

        // validUntil은 현재 시간으로부터 30분 이후의 시간이어야 한다.
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);
        assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

    private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }
}