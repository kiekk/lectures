package com.shyoon.wms.outbound.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public Outbound(
            final Long orderNo,
            final OrderCustomer orderCustomer,
            final String deliveryRequirements,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        validateConstructor(orderNo, orderCustomer, deliveryRequirements, outboundProducts, isPriorityDelivery, desiredDeliveryAt);

        this.orderNo = orderNo;
        this.orderCustomer = orderCustomer;
        this.deliveryRequirements = deliveryRequirements;
        this.outboundProducts = outboundProducts;
        this.isPriorityDelivery = isPriorityDelivery;
        this.desiredDeliveryAt = desiredDeliveryAt;
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

    public void assignNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
    }

    public Long getOutboundNo() {
        return outboundNo;
    }
}
