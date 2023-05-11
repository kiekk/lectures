package com.example.product.payment;

import com.example.product.DiscountPolicy;
import com.example.product.Product;
import com.example.product.order.Order;

class PaymentAdapter implements PaymentPort {

    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;

    PaymentAdapter(PaymentGateway paymentGateway, PaymentRepository paymentRepository) {
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Order getOrder(Long orderId) {
        return new Order(new Product("상품1", 1000, DiscountPolicy.NONE), 2);
    }

    @Override
    public void pay(int totalPrice, String cardNumber) {

        paymentGateway.execute(totalPrice, cardNumber);
    }

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }
}
