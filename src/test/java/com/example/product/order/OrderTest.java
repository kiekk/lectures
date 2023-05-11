package com.example.product.order;

import com.example.product.DiscountPolicy;
import com.example.product.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderTest {

    @Test
    void getTotalPriceNone() {
        final Order order = new Order(new Product("상품명", 1_000, DiscountPolicy.NONE), 2);
        final int totalPrice = order.getTotalPrice();

        assertThat(totalPrice).isEqualTo(2_000);
    }

    @Test
    void getTotalPriceFix1000() {
        final Order order = new Order(new Product("상품명", 2_000, DiscountPolicy.FIX_1000_AMOUNT), 2);
        final int totalPrice = order.getTotalPrice();

        assertThat(totalPrice).isEqualTo(2_000);
    }
}