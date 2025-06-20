package com.example.product.payment.application.service;

import com.example.product.order.domain.Order;
import com.example.product.payment.PaymentPort;
import com.example.product.payment.domain.Payment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payments")
class PaymentService {
    private final PaymentPort paymentPort;

    PaymentService(PaymentPort paymentPort) {
        this.paymentPort = paymentPort;
    }

    @Transactional
    @PostMapping("")
    public void payment(@RequestBody final PaymentRequest request) {
        Order order = paymentPort.getOrder(request.orderId());

        Payment payment = new Payment(order, request.cardNumber());

        paymentPort.pay(payment.getPrice(), payment.getCardNumber());
        paymentPort.save(payment);
    }
}
