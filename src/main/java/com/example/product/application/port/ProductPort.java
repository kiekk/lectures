package com.example.product.application.port;

import com.example.product.domain.Product;

interface ProductPort {
    void save(final Product product);

    Product getProduct(Long productId);
}
