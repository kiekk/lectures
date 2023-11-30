package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.product.domain.ProductFixture;

public class OrderProductFixture {
    private ProductFixture productFixture = ProductFixture.aProduct();
    private Long orderQuantity = 1L;
    private Long unitPrice = 1500L;

    public static OrderProductFixture anOrderProduct() {
        return new OrderProductFixture();
    }

    public OrderProductFixture product(final ProductFixture productFixture) {
        this.productFixture = productFixture;
        return this;
    }

    public OrderProductFixture orderQuantity(final Long orderQuantity) {
        this.orderQuantity = orderQuantity;
        return this;
    }

    public OrderProductFixture product(final Long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public OrderProduct build() {
        return new OrderProduct(
                productFixture.build(),
                orderQuantity,
                unitPrice
        );
    }
}
