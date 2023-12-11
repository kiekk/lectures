package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.inbound.domain.LPNFixture;
import com.shyoon.wms.location.domain.InventoriesFixture;
import com.shyoon.wms.location.domain.Inventory;
import com.shyoon.wms.location.domain.InventoryFixture;
import com.shyoon.wms.location.domain.LocationFixture;
import com.shyoon.wms.outbound.feature.Inventories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.shyoon.wms.inbound.domain.LPNFixture.*;
import static com.shyoon.wms.location.domain.InventoryFixture.*;
import static com.shyoon.wms.location.domain.LocationFixture.*;
import static org.assertj.core.api.Assertions.*;

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
                        anInventory()
                                .lpn(anLPN().expirationAt(LocalDateTime.now().minusDays(1)))
                                .location(aLocation()))
                .build();

        assertThatThrownBy(() -> inventories.validateInventory(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }

    @Test
    void filterFreshInventory() {
        final Inventory expiredInventory = anInventory()
                .lpn(anLPN().expirationAt(LocalDateTime.now().minusDays(1)))
                .build();

        final Inventory availableInventory = anInventory()
                .lpn(anLPN().expirationAt(LocalDateTime.now().plusDays(1)))
                .build();

        final List<Inventory> inventories_ = new ArrayList<>();
        inventories_.add(expiredInventory);
        inventories_.add(availableInventory);
        final Inventories inventories = new Inventories(inventories_);

        inventories.filterFreshInventory();

        assertThat(inventories.size()).isEqualTo(1);
    }

    @Test
    void sortEfficientInventoriesForPicking() {
        // 필터링 된 재고 목록 중 유통 기한이 가장 짧은 물품을 먼저 집품
        // 수량이 가장 적은 로케이션으로 가서 집품
        // 로케이션 순서로 정렬
        final Inventory inventory1 = anInventory()
                .location(aLocation().locationBarcode("L-1"))
                .lpn(anLPN().lpnBarcode("A1").expirationAt(LocalDateTime.now().plusDays(2L)))
                .build();

        final Inventory inventory2 = anInventory()
                .location(aLocation().locationBarcode("L-2"))
                .lpn(anLPN().lpnBarcode("A2").expirationAt(LocalDateTime.now().plusDays(1L)))
                .build();

        final Inventory inventory3 = anInventory()
                .inventoryQuantity(2L)
                .location(aLocation().locationBarcode("L-3"))
                .lpn(anLPN().lpnBarcode("A3").expirationAt(LocalDateTime.now().plusDays(1L)))
                .build();

        final List<Inventory> inventories_ = new ArrayList<>();
        inventories_.add(inventory1);
        inventories_.add(inventory2);
        inventories_.add(inventory3);
        final Inventories inventories = new Inventories(inventories_);

        final Inventories sorted = inventories.sortEfficientInventoriesForPicking();

        final List<Inventory> list = sorted.toList();

        assertThat(list.get(0)).isEqualTo(inventory3);
        assertThat(list.get(1)).isEqualTo(inventory2);
        assertThat(list.get(2)).isEqualTo(inventory1);
    }
}
