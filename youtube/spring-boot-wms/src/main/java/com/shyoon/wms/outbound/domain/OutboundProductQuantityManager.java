package com.shyoon.wms.outbound.domain;

public class OutboundProductQuantityManager {

    public void decreaseQuantity(final OutboundProducts splitProducts, final OutboundProducts targets) {
        decreaseOrderQuantity(splitProducts, targets);
        removeZeroQuantityProducts(targets);
    }

    void decreaseOrderQuantity(final OutboundProducts splitOutboundProducts, final OutboundProducts targets) {
        for (OutboundProduct splitProduct : splitOutboundProducts.toList()) {
            final OutboundProduct target = targets.getOutboundProductBy(splitProduct.getProductNo());
            target.decreaseOrderQuantity(splitProduct.getOrderQuantity());
        }
    }

    void removeZeroQuantityProducts(final OutboundProducts targets) {
        targets.removeIf();
    }
}
