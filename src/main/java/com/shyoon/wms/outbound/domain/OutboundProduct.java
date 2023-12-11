package com.shyoon.wms.outbound.domain;

import com.google.common.annotations.VisibleForTesting;
import com.shyoon.wms.location.domain.Inventory;
import com.shyoon.wms.outbound.feature.Inventories;
import com.shyoon.wms.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

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

    private List<Picking> pickings = new ArrayList<>();

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

    public void allocatePicking(final Inventories inventories) {
        final Inventories inventories1 = inventories.makeEfficientInventoriesForPicking(this.getProductNo());

        final List<Picking> pickings = createPickings(inventories1);

        allocatePickings(pickings);
    }

    private void allocatePickings(List<Picking> pickings) {
        pickings.forEach(picking -> picking.assignOutboundProduct(this));
        this.pickings = pickings;
    }

    private List<Picking> createPickings(final Inventories inventories1) {
        final List<Inventory> inventories = inventories1.toList();
        final Inventory firstInventory = inventories.get(0);
        if (firstInventory.getInventoryQuantity() >= orderQuantity) {
            return List.of(new Picking(firstInventory, orderQuantity));
        }

        Long remainingQuantity = orderQuantity;
        final List<Picking> pickings = new ArrayList<>();

        // 재고 수량이 가장 많은 순으로 정렬된 재고
        for (Inventory inventory : inventories) {
            if (remainingQuantity <= 0) {
                break;
            }
            final Long quantityToAllocate = Math.min(
                    inventory.getInventoryQuantity(),
                    remainingQuantity
            );
            remainingQuantity -= quantityToAllocate;
            pickings.add(new Picking(inventory, quantityToAllocate));
        }
        return pickings;
    }
}
