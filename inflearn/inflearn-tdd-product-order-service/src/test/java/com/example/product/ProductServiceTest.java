package com.example.product;

import com.example.product.application.service.GetProductResponse;
import com.example.product.application.service.ProductService;
import com.example.product.application.service.UpdateProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void 상품조회() {
        // 상품등록
        productService.addProduct(ProductSteps.상품등록요청_생성());
        final long productId = 1L;

        // 상품 조회
        final GetProductResponse response = productService.getProduct(productId);

        // 상품의 응답 검증
        assertThat(response).isNotNull();
    }

    @Test
    void 상품수정() {
        productService.addProduct(ProductSteps.상품등록요청_생성());
        final Long productId = 1L;
        final UpdateProductRequest request = ProductSteps.상품수정요청_생성();

        productService.updateProduct(productId, request);
        final GetProductResponse product = productService.getProduct(productId);
        assertThat(product.name()).isEqualTo("상품 수정");
        assertThat(product.price()).isEqualTo(2000);
    }

}
