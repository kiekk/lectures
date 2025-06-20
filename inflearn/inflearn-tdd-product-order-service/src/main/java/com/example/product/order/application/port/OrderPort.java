package com.example.product.order.application.port;

import com.example.product.domain.Product;
import com.example.product.order.domain.Order;

public interface OrderPort {
    Product getProductById(final Long productId);

    void save(final Order order);
}
