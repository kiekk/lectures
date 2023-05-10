package com.example.product.payment;

interface PaymentGateway {
    void execute(final Payment payment);
}
