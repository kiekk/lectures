package com.example.product.payment;

import com.example.product.order.Order;
import org.springframework.stereotype.Component;

@Component
class PaymentService {
    private final PaymentPort paymentPort;

    PaymentService(PaymentPort paymentPort) {
        this.paymentPort = paymentPort;
    }

    public void payment(final PaymentRequest request) {
        Order order = paymentPort.getOrder(request.orderId());

        Payment payment = new Payment(order, request.cardNumber());

        paymentPort.pay(payment.price(), payment.cardNumber());
        paymentPort.save(payment);
    }
}
