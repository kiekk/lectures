package com.example.product.payment;

interface PaymentGateway {
    void execute(final int totalPrice, final String cardNumber);
}
