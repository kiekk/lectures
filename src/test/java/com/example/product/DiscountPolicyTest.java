package com.example.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DiscountPolicyTest {

    @Test
    void noneDiscountPolicy() {
        final int price = 1_000;
        final int discountedPrice = DiscountPolicy.NONE.applyDiscount(price);

        assertThat(discountedPrice).isEqualTo(price);
    }

    @Test
    void fix1000DiscountPolicy() {
        final int price = 2_000;
        final int discountedPrice = DiscountPolicy.FIX_1000_AMOUNT.applyDiscount(price);

        assertThat(discountedPrice).isEqualTo(1_000);
    }
}