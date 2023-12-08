package com.shyoon.wms.outbound.domain;

import com.google.common.annotations.VisibleForTesting;
import com.shyoon.wms.outbound.feature.Inventories;
import com.shyoon.wms.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Getter
@Entity
@Table(name = "outbound_product")
@Comment("출고 상품")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboundProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbound_product_no")
    @Comment("출고 상품 번호")
    private Long outboundProductNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    @Comment("출고 상품")
    private Product product;

    @Column(name = "order_quantity", nullable = false)
    @Comment("주문 수량")
    private Long orderQuantity;

    @Column(name = "unit_price", nullable = false)
    @Comment("단가")
    private Long unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outbound_no", nullable = false)
    @Comment("출고 번호")
    private Outbound outbound;

    public OutboundProduct(
            final Product product,
            final Long orderQuantity,
            final Long unitPrice) {
        validateConstructor(product, orderQuantity, unitPrice);

        this.product = product;
        this.orderQuantity = orderQuantity;
        this.unitPrice = unitPrice;
    }

    @VisibleForTesting
    OutboundProduct(
            final Long outboundProductNo,
            final Product product,
            final Long orderQuantity,
            final Long unitPrice) {
        this(product, orderQuantity, unitPrice);
        this.outboundProductNo = outboundProductNo;
    }

    public boolean isSameProductNo(Long productNo) {
        return getProductNo().equals(productNo);
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

    public void assignOutbound(final Outbound outbound) {
        this.outbound = outbound;
    }

    public Long getProductNo() {
        return product.getProductNo();
    }

    public OutboundProduct split(final Long splitQuantity) {
        if (splitQuantity > orderQuantity) {
            throw new IllegalArgumentException("분할 수량은 주문 수량보다 작아야 합니다.");
        }
        return new OutboundProduct(
                product,
                splitQuantity,
                unitPrice
        );
    }

    public Long calculateOutboundProductWeight() {
        return product.getWeightInGrams() * orderQuantity;
    }

    public Long calculateOutboundProductVolume() {
        return product.getProductSize().getVolume() * orderQuantity;
    }

    public void decreaseOrderQuantity(final Long quantity) {
        if (quantity > this.orderQuantity) {
            throw new IllegalArgumentException("주문 수량보다 많은 수량을 출고할 수 없습니다.");
        }

        this.orderQuantity -= quantity;
    }

    boolean isZeroQuantity() {
        return getOrderQuantity() == 0;
    }

    public void allocatePicking(Inventories inventories) {

    }
}
