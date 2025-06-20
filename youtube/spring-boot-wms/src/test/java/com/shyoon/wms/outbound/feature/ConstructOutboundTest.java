package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.inbound.domain.LPNFixture;
import com.shyoon.wms.location.domain.InventoriesFixture;
import com.shyoon.wms.location.domain.InventoryFixture;
import com.shyoon.wms.outbound.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConstructOutboundTest {

    ConstructOutbound sut = new ConstructOutbound();

    @Test
    @DisplayName("출고를 생성한다.")
    void createOutbound() {
        final Outbound outbound = sut.create(
                List.of(InventoriesFixture.anInventories().build()),
                PackagingMaterialsFixture.aPackagingMaterials().build(),
                OrderFixture.anOrder().build(),
                false,
                LocalDate.now());

        assertThat(outbound).isNotNull();
    }

    @Test
    @DisplayName("출고를 생성한다. - 출고 수량이 재고 수량보다 많을 경우 예외가 발생한다.")
    void fail_over_quantity_createOutbound() {
        assertThatThrownBy(() -> {
            sut.create(
                    List.of(InventoriesFixture.anInventories().build()),
                    PackagingMaterialsFixture.aPackagingMaterials().build(),
                    OrderFixture.anOrder().orderProduct(
                            OrderProductFixture.anOrderProduct().orderQuantity(2L)
                    ).build(),
                    false,
                    LocalDate.now());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }

    @Test
    @DisplayName("출고를 생성한다. - (유통기한이 지나서 재고가 부족)출고 수량이 재고 수량보다 많을 경우 예외가 발생한다.")
    void expire_createOutbound() {
        assertThatThrownBy(() -> sut.create(
                List.of(InventoriesFixture.anInventories()
                        .inventories(InventoryFixture.anInventory()
                                .lpn(LPNFixture.anLPN().expirationAt(LocalDateTime.now().minusDays(1))))
                        .build()),
                PackagingMaterialsFixture.aPackagingMaterials().build(),
                OrderFixture.anOrder().build(),
                false,
                LocalDate.now())).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }

    @Test
    @DisplayName("출고를 생선한다. - 주문을 포장할 포장재를 찾을 수 없다. - 제한 무게를 초과")
    void over_max_weight_createOutbound() {
        assertThatThrownBy(() -> sut.create(
                List.of(InventoriesFixture.anInventories().build()),
                PackagingMaterialsFixture.aPackagingMaterials().packagingMaterials(
                        PackagingMaterialFixture.aPackagingMaterial().maxWeightInGrams(1L)
                ).build(),
                OrderFixture.anOrder().build(),
                false,
                LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출고를 생선한다. - 주문을 포장할 포장재를 찾을 수 없다. - 허용 가능한 부피 초과")
    void over_volume_createOutbound() {
        assertThatThrownBy(() -> sut.create(
                List.of(InventoriesFixture.anInventories().build()),
                PackagingMaterialsFixture.aPackagingMaterials().packagingMaterials(
                        PackagingMaterialFixture.aPackagingMaterial().packagingMaterialDimension(
                                PackagingMaterialDimensionFixture.aPackagingMaterialDimension()
                                        .innerHeightInMillimeters(1L)
                                        .innerWidthInMillimeters(1L)
                        )
                ).build(),
                OrderFixture.anOrder().build(),
                false,
                LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("적합한 포장재가 없습니다.");
    }

}
