package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.product.domain.Product;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class OutboundProduct {
    private final Product product;
    private final Long orderQuantity;
    private final Long unitPrice;

    public OutboundProduct(
            final Product product,
            final Long orderQuantity,
            final Long unitPrice) {
        validateConstructor(product, orderQuantity, unitPrice);

        this.product = product;
        this.orderQuantity = orderQuantity;
        this.unitPrice = unitPrice;
    }

    private void validateConstructor(
            final Product product,
            final Long orderQuantity,
            final Long unitPrice) {
        Assert.notNull(product, "상품은 필수입니다.");
        Assert.notNull(product, "주문 수량은 필수입니다.");
        if (orderQuantity < 1) {
            throw new IllegalArgumentException("주문 수량은 1개 이상이어야 합니다.");
        }

        Assert.notNull(unitPrice, "단가는 필수입니다.");
        if (unitPrice < 1) {
            throw new IllegalArgumentException("단가는 1원 이상이어야 합니다.");
        }
    }
}
