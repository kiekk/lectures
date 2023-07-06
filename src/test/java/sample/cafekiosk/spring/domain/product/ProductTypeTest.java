package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType() {
        // given
        ProductType type = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(type);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType2() {
        // given
        ProductType type = ProductType.BAKERY;

        // when
        boolean result = ProductType.containsStockType(type);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType3() {
        // given
        ProductType handmade = ProductType.HANDMADE;
        ProductType bottle = ProductType.BOTTLE;
        ProductType bakery = ProductType.BAKERY;

        // when
        boolean handmadeResult = ProductType.containsStockType(handmade);
        boolean bottleResult = ProductType.containsStockType(bottle);
        boolean bakeryResult = ProductType.containsStockType(bakery);

        // then
        assertThat(handmadeResult).isFalse();
        assertThat(bottleResult).isTrue();
        assertThat(bakeryResult).isTrue();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"})
    @ParameterizedTest
    void containsStockType4(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }
}