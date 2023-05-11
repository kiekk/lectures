package com.example.product.payment;

import com.example.product.order.Order;

interface PaymentPort {
    Order getOrder(final Long orderId);

    void pay(int totalPrice, String cardNumber);

    void save(final Payment payment);
}
