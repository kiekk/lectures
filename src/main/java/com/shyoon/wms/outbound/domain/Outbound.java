package com.shyoon.wms.outbound.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "outbound", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OutboundProduct> outboundProducts = new ArrayList<>();

    @Column(name = "is_priority_delivery", nullable = false)
    @Comment("우선 출고 여부")
    private Boolean isPriorityDelivery;

    @Column(name = "desired_delivery_at", nullable = false)
    @Comment("희망 출고일")
    private LocalDate desiredDeliveryAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packaging_material_no")
    private PackagingMaterial recommendedPackagingMaterial;

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
        this.outboundProducts = outboundProducts;
        this.isPriorityDelivery = isPriorityDelivery;
        this.desiredDeliveryAt = desiredDeliveryAt;
        this.recommendedPackagingMaterial = recommendedPackagingMaterial;
        outboundProducts.forEach(outboundProduct -> outboundProduct.assignOutbound(this));
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

    public OutboundProduct splitOutboundProducts(final Long productNo,
                                                 final Long quantity) {
        final OutboundProduct outboundProduct = getOutboundProductBy(productNo);

        return outboundProduct.split(quantity);
    }

    private OutboundProduct getOutboundProductBy(Long productNo) {
        return outboundProducts.stream()
                .filter(op -> op.isSameProductNo(productNo))
                .findFirst()
                .orElseThrow();
    }

    public Outbound split(final List<OutboundProduct> splitOutboundProducts) {
        // 분할할 상품 목록의 수량이 기존 출고 상품 목록의 수량보다 많으면 예외를 던진다.
        final Long totalOrderQuantity = calculateTotalOrderQuantity();
        final Long splitTotalQuantity = splitTotalQuantity(splitOutboundProducts);
        if (totalOrderQuantity <= splitTotalQuantity) {
            throw new IllegalArgumentException("분할할 수량이 출고 수량보다 같거나 많습니다.");
        }

        return new Outbound(
                orderNo,
                orderCustomer,
                deliveryRequirements,
                splitOutboundProducts,
                isPriorityDelivery,
                desiredDeliveryAt,
                null
        );
    }

    private Long splitTotalQuantity(final List<OutboundProduct> splitOutboundProducts) {
        return splitOutboundProducts.stream()
                .mapToLong(OutboundProduct::getOrderQuantity)
                .sum();
    }

    private Long calculateTotalOrderQuantity() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::getOrderQuantity)
                .sum();
    }
}
