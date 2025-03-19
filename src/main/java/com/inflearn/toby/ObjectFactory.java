package com.inflearn.toby;

public class ObjectFactory {

    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    public ExRateProvider exRateProvider() {
        return new SimpleExRateProvider();
    }
}
