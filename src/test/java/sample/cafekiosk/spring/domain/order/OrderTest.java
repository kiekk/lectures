package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static sample.cafekiosk.spring.domain.order.OrderStatus.INIT;

class OrderTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        // given
        List<Product> products = List.of(
                createProduct("001", 1_000),
                createProduct("002", 2_000),
                createProduct("003", 3_000)
        );

        // when
        Order order = Order.create(products);

        // then
        assertThat(order.getTotalPrice()).isEqualTo(6_000);
    }

    @DisplayName("주문 생성 시 주문 상태는 INIT이다.")
    @Test
    void orderStatusInit() {
        // given
        List<Product> products = List.of(
                createProduct("001", 1_000),
                createProduct("002", 2_000),
                createProduct("003", 3_000)
        );

        // when
        Order order = Order.create(products);

        // then
        assertThat(order.getStatus()).isEqualByComparingTo(INIT);
    }


    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("메뉴 이름")
                .price(price)
                .build();
    }
}