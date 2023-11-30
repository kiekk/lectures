package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.outbound.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterOutboundUnitTest {

    private RegisterOutbound registerOutbound;

    @BeforeEach
    void setUp() {
        registerOutbound = new RegisterOutbound(null, null, null, null);
    }

    @Test
    @DisplayName("주문한 상품을 포장할 수 있는 포장재를 찾는다.")
    void getOptimalPackagingMaterial() {
        final Order order = OrderFixture.anOrder().build();
        final PackagingMaterial packagingMaterial = PackagingMaterialFixture.aPackagingMaterial().build();
        final PackagingMaterial optimalPackagingMaterial = registerOutbound.getOptimalPackagingMaterial(List.of(packagingMaterial), order);

        assertThat(optimalPackagingMaterial).isNotNull();
    }

    @Test
    @DisplayName("주문한 상품을 포장할 수 있는 포장재를 찾는다.")
    void empty_getOptimalPackagingMaterial() {
        final Order order = OrderFixture.anOrder().orderProduct(
                OrderProductFixture.anOrderProduct().orderQuantity(100L)
        ).build();
        final PackagingMaterial packagingMaterial = PackagingMaterialFixture.aPackagingMaterial().build();
        final PackagingMaterial optimalPackagingMaterial = registerOutbound.getOptimalPackagingMaterial(List.of(packagingMaterial), order);

        assertThat(optimalPackagingMaterial).isNull();
    }

}
