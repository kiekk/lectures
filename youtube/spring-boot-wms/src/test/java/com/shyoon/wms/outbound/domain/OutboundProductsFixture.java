package com.shyoon.wms.outbound.domain;

import java.util.ArrayList;
import java.util.List;

public class OutboundProductsFixture {

    private List<OutboundProductFixture> outboundProducts = List.of(OutboundProductFixture.anOutboundProduct());

    public static OutboundProductsFixture anOutboundProducts() {
        return new OutboundProductsFixture();
    }

    public OutboundProductsFixture outboundProducts(final OutboundProductFixture... outboundProducts) {
        this.outboundProducts = List.of(outboundProducts);
        return this;
    }

    public OutboundProducts build() {
        return new OutboundProducts(buildOutboundProducts());
    }

    private List<OutboundProduct> buildOutboundProducts() {
        return outboundProducts.stream()
                .map(OutboundProductFixture::build)
                .toList();
    }
}
