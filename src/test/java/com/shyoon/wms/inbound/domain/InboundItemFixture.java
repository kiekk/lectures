package com.shyoon.wms.inbound.domain;

import com.shyoon.wms.product.fixture.ProductFixture;

public class InboundItemFixture {

    private Long inboundItemNo = 1L;
    private ProductFixture product = ProductFixture.aProduct();
    private Long quantity = 1L;
    private Long unitPrice = 1500L;
    private String description = "description";

    public static InboundItemFixture anInboundItem() {
        return new InboundItemFixture();
    }

    public InboundItemFixture inboundItemNo(Long inboundItemNo) {
        this.inboundItemNo = inboundItemNo;
        return this;
    }

    public InboundItemFixture product(ProductFixture product) {
        this.product = product;
        return this;
    }

    public InboundItemFixture quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public InboundItemFixture unitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public InboundItemFixture description(String description) {
        this.description = description;
        return this;
    }

    public InboundItem build() {
        return new InboundItem(
                inboundItemNo,
                product.build(),
                quantity,
                unitPrice,
                description
        );
    }
}
