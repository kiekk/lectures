package sample.cafekiosk.spring.api.service.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.BOTTLE;

class ProductNumberFactoryTest extends IntegrationTestSupport {

    @Autowired
    private ProductNumberFactory productNumberFactory;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("상품 등록 시 등록된 상품이 없을 경우 상품 번호는 '001'이 반환된다.")
    @Test
    void createNextProductNumberWithEmptyProducts() {
        // when
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        // then
        assertThat(nextProductNumber).isEqualTo("001");
    }

    @DisplayName("상품 등록 시 등록된 상품이 있을 경우 상품 번호는 등록된 상품의 개수에서 + 1 값이 '00X' 형태로 반환된다.")
    @Test
    void createNextProductNumberWithProducts() {
        //given
        Product product = createProduct(BOTTLE, "001", 1_000, SELLING, "아메리카노");
        productRepository.save(product);

        // when
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        // then
        assertThat(nextProductNumber).isEqualTo("002");
    }

    private Product createProduct(ProductType type, String productNumber, int price, ProductSellingStatus status, String name) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(status)
                .name(name)
                .price(price)
                .build();
    }

}