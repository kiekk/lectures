package com.shyoon.wms.product.feature;

import com.shyoon.wms.product.common.ApiTest;
import com.shyoon.wms.product.domain.ProductRepository;
import com.shyoon.wms.product.feature.api.RegisterProductApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterProductTest extends ApiTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품을 등록한다.")
    void registerProduct() {
        new RegisterProductApi().request();

        assertThat(productRepository.findAll()).hasSize(1);
    }

}
