package com.inflearn.toby.payment;

import com.inflearn.toby.exrate.provider.WebApiExRateProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceTest {

    @Test
    @DisplayName("prepare 메서드의 요구사항 3가지를 잘 충족했는지 검증, 1.적용환율 2.원화 환산 금액 3.원화 환산 금액 유효시간")
    void prepare() throws IOException {
        // given
        PaymentService paymentService = new PaymentService(new WebApiExRateProvider());

        // when
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.TEN);

        // then
        // 적용환율
        assertThat(payment.getExRate()).isNotNull();
        // 원화 환산 금액
        assertThat(payment.getConvertedAmount()).isEqualTo(payment.getExRate().multiply(payment.getForeignCurrencyAmount()));
        // 원호 환산 금액 유효시간
        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }
}