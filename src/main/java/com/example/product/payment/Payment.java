package com.example.product.payment;

import com.example.product.order.Order;
import org.springframework.util.Assert;

record Payment(Order order, String cardNumber) {
    private static Long id;

    Payment {
        Assert.notNull(order, "주문은 필수입니다.");
        Assert.hasText(cardNumber, "카드 번호는 필수입니다.");
    }

    public void assignId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int price() {
        return order.getTotalPrice();
    }
}
