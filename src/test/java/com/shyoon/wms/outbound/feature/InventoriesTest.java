package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.inbound.domain.LPNFixture;
import com.shyoon.wms.location.domain.Inventory;
import com.shyoon.wms.location.domain.LocationFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class InventoriesTest {

    @Test
    @DisplayName("주문한 상품을 출고할 수 있는 재고가 있는지 확인한다.")
    void validateInventory() {
        final Inventory inventory = new Inventory(
                LocationFixture.aLocation().build(),
                LPNFixture.anLPN().build()
        );
        new Inventories(List.of(inventory), 1L).validateInventory();
    }

    @Test
    @DisplayName("주문한 상품을 출고할 수 있는 재고가 있는지 확인한다.")
    void fail_over_quantity_validateInventory() {
        final Inventory inventory = new Inventory(
                LocationFixture.aLocation().build(),
                LPNFixture.anLPN().build()
        );
        assertThatThrownBy(() -> new Inventories(List.of(inventory), 2L).validateInventory())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");

    }

    @Test
    @DisplayName("주문한 상품을 출고할 수 있는 재고가 있는지 확인한다.")
    void expire_validateInventory() {
        final Inventory inventory = new Inventory(
                LocationFixture.aLocation().build(),
                LPNFixture.anLPN().expirationAt(LocalDateTime.now().minusDays(1)).build()
        );
        assertThatThrownBy(() -> new Inventories(List.of(inventory), 1L).validateInventory())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }
}
