package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.product.domain.ProductRepository;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRepository {

    private final ProductRepository productRepository;

    public OrderRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Order getBy(final Long orderNo) {
        return new Order(
                orderNo,
                new OrderCustomer(
                        "name",
                        "email",
                        "phone",
                        "zipNo",
                        "address"
                ),
                "배송 요구사항",
                List.of(new OrderProduct(
                        productRepository.getBy(1L),
                        1L,
                        1500L
                ))
        );
    }

}
