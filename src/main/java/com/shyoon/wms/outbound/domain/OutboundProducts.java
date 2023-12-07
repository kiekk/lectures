package com.shyoon.wms.outbound.domain;

import java.util.List;

public record OutboundProducts(List<OutboundProduct> outboundProducts) {
    Long splitTotalQuantity() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::getOrderQuantity)
                .sum();
    }
}
