package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.product.domain.ProductFixture;

public class OutboundProductFixture {

    private Long outboundProductNo = 1L;
    private ProductFixture product = ProductFixture.aProduct();
    private Long orderQuantity = 1L;
    private Long unitPrice = 1500L;

    public static OutboundProductFixture anOutboundProduct() {
        return new OutboundProductFixture();
    }

    public OutboundProductFixture outboundProductNo(final Long outboundProductNo) {
        this.outboundProductNo = outboundProductNo;
        return this;
    }

    public OutboundProductFixture product(final ProductFixture product) {
        this.product = product;
        return this;
    }

    public OutboundProductFixture orderQuantity(final Long orderQuantity) {
        this.orderQuantity = orderQuantity;
        return this;
    }

    public OutboundProductFixture unitPrice(final Long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public OutboundProduct build() {
        return new OutboundProduct(
                outboundProductNo,
                product.build(),
                orderQuantity,
                unitPrice
        );
    }
}
