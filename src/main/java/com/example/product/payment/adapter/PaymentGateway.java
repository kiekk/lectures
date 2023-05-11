package com.example.product.payment.adapter;

interface PaymentGateway {
    void execute(final int totalPrice, final String cardNumber);
}
