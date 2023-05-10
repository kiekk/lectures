package com.example.product.payment;

import com.example.product.order.Order;

interface PaymentPort {
    Order getOrder(final Long orderId);

    void pay(final Payment payment);

    void save(final Payment payment);
}
