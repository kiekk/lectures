package com.example.product.payment.application.port;

import com.example.product.order.domain.Order;
import com.example.product.payment.domain.Payment;

interface PaymentPort {
    Order getOrder(final Long orderId);

    void pay(int totalPrice, String cardNumber);

    void save(final Payment payment);
}
