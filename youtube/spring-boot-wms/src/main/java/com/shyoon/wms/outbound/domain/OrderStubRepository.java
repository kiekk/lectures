package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.product.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderStubRepository implements OrderRepository {

    private final ProductRepository productRepository;

    public OrderStubRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
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
