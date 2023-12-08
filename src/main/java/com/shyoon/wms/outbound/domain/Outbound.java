package com.shyoon.wms.outbound.domain;

import com.google.common.annotations.VisibleForTesting;
import com.shyoon.wms.location.domain.Location;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Table(name = "outbound")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbound_no")
    @Comment("출고 번호")
    private Long outboundNo;

    @Column(name = "order_no")
    @Comment("주문 번호")
    private Long orderNo;

    @Embedded
    private OrderCustomer orderCustomer;

    @Column(name = "delivery_requirements", nullable = false)
    @Comment("배송 요구사항")
    private String deliveryRequirements;

    @Embedded
    public OutboundProducts outboundProducts;

    @Column(name = "is_priority_delivery", nullable = false)
    @Comment("우선 출고 여부")
    private Boolean isPriorityDelivery;

    @Column(name = "desired_delivery_at", nullable = false)
    @Comment("희망 출고일")
    private LocalDate desiredDeliveryAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packaging_material_no")
    private PackagingMaterial recommendedPackagingMaterial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picking_tote_no")
    @Comment("피킹 토트 번호")
    private Location pikingTote;

    public Outbound(
            final Long orderNo,
            final OrderCustomer orderCustomer,
            final String deliveryRequirements,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt,
            final PackagingMaterial recommendedPackagingMaterial) {
        validateConstructor(orderNo, orderCustomer, deliveryRequirements, outboundProducts, isPriorityDelivery, desiredDeliveryAt);

        this.orderNo = orderNo;
        this.orderCustomer = orderCustomer;
        this.deliveryRequirements = deliveryRequirements;
        this.outboundProducts = new OutboundProducts(outboundProducts);
        this.isPriorityDelivery = isPriorityDelivery;
        this.desiredDeliveryAt = desiredDeliveryAt;
        this.recommendedPackagingMaterial = recommendedPackagingMaterial;
        outboundProducts.forEach(outboundProduct -> outboundProduct.assignOutbound(this));
    }

    @VisibleForTesting
    Outbound(
            final Long orderNo,
            final OrderCustomer orderCustomer,
            final String deliveryRequirements,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt,
            final PackagingMaterial packagingMaterial,
            final Location pikingTote) {
        this(orderNo, orderCustomer, deliveryRequirements, outboundProducts, isPriorityDelivery, desiredDeliveryAt, packagingMaterial);
        this.pikingTote = pikingTote;
    }

    private void validateConstructor(
            final Long orderNo,
            final OrderCustomer orderCustomer,
            final String deliveryRequirements,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        Assert.notNull(orderNo, "주문번호는 필수입니다.");
        Assert.notNull(orderCustomer, "주문고객은 필수입니다.");
        Assert.notNull(deliveryRequirements, "배송요구사항은 필수입니다.");
        Assert.notEmpty(outboundProducts, "출고상품은 필수입니다.");
        Assert.notNull(isPriorityDelivery, "우선출고여부는 필수입니다.");
        Assert.notNull(desiredDeliveryAt, "희망출고일은 필수입니다.");
    }

    public Outbound split(final OutboundProducts splitOutboundProducts) {
        // 분할할 상품 목록의 수량이 기존 출고 상품 목록의 수량보다 많으면 예외를 던진다.
        validateSplit(splitOutboundProducts);

        return new Outbound(
                orderNo,
                orderCustomer,
                deliveryRequirements,
                splitOutboundProducts.toList(),
                isPriorityDelivery,
                desiredDeliveryAt,
                null
        );
    }

    private void validateSplit(final OutboundProducts splitOutboundProducts) {
        final Long totalOrderQuantity = outboundProducts.calculateTotalOrderQuantity();
        final Long splitTotalQuantity = splitOutboundProducts.splitTotalQuantity();
        if (totalOrderQuantity <= splitTotalQuantity) {
            throw new IllegalArgumentException("분할할 수량이 출고 수량보다 같거나 많습니다.");
        }
    }

    public void assignPackagingMaterial(final PackagingMaterial packagingMaterial) {
        this.recommendedPackagingMaterial = packagingMaterial;
    }

    public void allocatePickingTote(final Location tote) {
        validateToteAllocation(tote);
        pikingTote = tote;
    }

    private void validateToteAllocation(Location tote) {
        // 1. null 체크
        Assert.notNull(tote, "출고에 할닫할 토트는 필수 입니다.");

        // 2. 로케이션 토트가 맞는지
        if (!tote.isTote()) {
            throw new IllegalArgumentException("할당하려는 로케이션이 토트가 아닙니다.");
        }

        // 3. 토트에 상품이 담겨있지는 않은지
        if (tote.hasAvailableInventory()) {
            throw new IllegalArgumentException("할당하려는 토트에 상품이 존재합니다.");
        }

        // 4. 이미 출고에 토트가 할당되어 있는지
        if (pikingTote != null) {
            throw new IllegalStateException("이미 출고에 토트가 할당되어 있습니다.");
        }
        // 5. 포장재가 할당되어있는지 (포장재가 할당이 되어 있지 않으면 출고 불가능)
        if (recommendedPackagingMaterial == null) {
            throw new IllegalStateException("포장재가 할당되어 있지 않습니다.");
        }
    }
}
