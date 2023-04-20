package com.example.product;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class ProductServiceTest {

    private static class AddProductRequest {

        private final String name;
        private final int price;
        private final DiscountPolicy discountPolicy;


        public AddProductRequest(final String name, final int price, final DiscountPolicy discountPolicy) {
            Assert.hasText(name, "상품명은 필수입니다.");
            Assert.isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
            Assert.notNull(discountPolicy, "할인 정책은 필수입니다.");
            this.name = name;
            this.price = price;
            this.discountPolicy = discountPolicy;
        }
    }

    private enum DiscountPolicy {
        NONE
    }
}
