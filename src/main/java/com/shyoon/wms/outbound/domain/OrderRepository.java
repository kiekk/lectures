package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.product.domain.ProductRepository;

import java.util.List;

public class OrderRepository {

    private final ProductRepository productRepository;

    public OrderRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Order getBy(final Long orderNo) {
        final OrderCustomer orderCustomer = new OrderCustomer(
                "name",
                "email",
                "phone",
                "zipNo",
                "address"
        );
        final String deliveryRequirements = "배송 요구사항";

        final Long productNo = 1L;
        final Long orderQuantity = 1500L;
        final Long unitPrice = 1L;

        final OrderProduct orderProduct = new OrderProduct(
                productRepository.getBy(productNo),
                orderQuantity,
                unitPrice
        );
        final List<OrderProduct> orderProducts = List.of(orderProduct);

        return new Order(
                orderNo,
                orderCustomer,
                deliveryRequirements,
                orderProducts
        );
    }

}
