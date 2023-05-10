package com.example.product.order;

import com.example.product.Product;

public interface OrderPort {
    Product getProductById(final Long productId);

    void save(final Order order);
}
