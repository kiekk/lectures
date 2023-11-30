package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.inbound.domain.LPNFixture;
import com.shyoon.wms.location.domain.InventoriesFixture;
import com.shyoon.wms.location.domain.InventoryFixture;
import com.shyoon.wms.location.domain.LocationFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InventoriesTest {

    @Test
    @DisplayName("주문한 상품을 출고할 수 있는 재고가 있는지 확인한다.")
    void validateInventory() {
        final Inventories inventories = InventoriesFixture.anInventories().build();

        inventories.validateInventory(1L);
    }

    @Test
    @DisplayName("주문한 상품을 출고할 수 있는 재고가 있는지 확인한다.")
    void fail_over_quantity_validateInventory() {
        final Inventories inventories = InventoriesFixture.anInventories().build();
        assertThatThrownBy(() -> inventories.validateInventory(2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");

    }

    @Test
    @DisplayName("주문한 상품을 출고할 수 있는 재고가 있는지 확인한다.")
    void expire_validateInventory() {
        final Inventories inventories = InventoriesFixture.anInventories()
                .inventories(
                        InventoryFixture.anInventory()
                                .lpn(LPNFixture.anLPN().expirationAt(LocalDateTime.now().minusDays(1)))
                                .location(LocationFixture.aLocation()))
                .build();

        assertThatThrownBy(() -> inventories.validateInventory(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }
}
