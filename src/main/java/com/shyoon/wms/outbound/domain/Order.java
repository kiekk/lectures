package com.shyoon.wms.outbound.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Order {
    private final Long orderNo;
    private final OrderCustomer orderCustomer;
    private final String deliveryRequirements;
    private final List<OrderProduct> orderProducts;

    public Order(
            final Long orderNo,
            final OrderCustomer orderCustomer,
            final String deliveryRequirements,
            final List<OrderProduct> orderProducts) {
        this.orderNo = orderNo;
        this.orderCustomer = orderCustomer;
        this.deliveryRequirements = deliveryRequirements;
        this.orderProducts = orderProducts;
    }
}
