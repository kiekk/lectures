package com.inflearn.toby;

import com.inflearn.toby.exrate.provider.WebApiExRateProvider;
import com.inflearn.toby.payment.ExRateProvider;
import com.inflearn.toby.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectFactory {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }
}
