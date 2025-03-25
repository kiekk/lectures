package com.inflearn.toby.payment;

import com.inflearn.toby.ObjectFactory;
import com.inflearn.toby.TestObjectFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceSpringTest {

    @Test
    @DisplayName("AnnotationConfigApplicationContext 객체를 사용하여 PaymentService Bean 주입 테스트")
    void prepare() throws IOException {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestObjectFactory.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1_000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));
    }
}