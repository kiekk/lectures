package com.inflearn.toby;

import java.io.IOException;
import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) throws IOException {
        WebApiExRateProvider exRateProvider = new WebApiExRateProvider();
        PaymentService paymentService = new PaymentService(exRateProvider);
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}
