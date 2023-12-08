package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.location.domain.InventoriesFixture;
import com.shyoon.wms.location.domain.Location;
import com.shyoon.wms.location.domain.StorageType;
import com.shyoon.wms.outbound.feature.Inventories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.shyoon.wms.location.domain.InventoriesFixture.*;
import static com.shyoon.wms.location.domain.InventoryFixture.anInventory;
import static com.shyoon.wms.location.domain.LocationFixture.aLocation;
import static com.shyoon.wms.outbound.domain.OutboundFixture.anOutbound;
import static org.assertj.core.api.Assertions.*;

class OutboundTest {

    @Test
    @DisplayName("출고에 토트를 할당한다.")
    void allocatePickingTote() {
        final Outbound outbound = anOutbound()
                .pickingTote(null)
                .build();
        final Location tote = aLocation().build();

        outbound.allocatePickingTote(tote);

        assertThat(outbound.getPikingTote()).isNotNull();
    }

    @Test
    @DisplayName("출고에 전달한 토트가 null 이면 예외가 발생한다.")
    void allocatePickingTote2() {
        final Outbound outbound = anOutbound().build();
        final Location tote = null;

        assertThatThrownBy(() -> outbound.allocatePickingTote(tote))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("출고에 할닫할 토트는 필수 입니다.");
    }

    @Test
    @DisplayName("출고에 전달한 토트가 로케이션 토트가 아니면 예외가 발생한다.")
    void allocatePickingTote3() {
        final Outbound outbound = anOutbound().build();
        final Location tote = aLocation().storageType(StorageType.PALLET).build();

        assertThatThrownBy(() -> outbound.allocatePickingTote(tote))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할당하려는 로케이션이 토트가 아닙니다.");
    }

    @Test
    @DisplayName("출고에 전달한 토트에 상품이 이미 담겨 있으면 예외가 발생한다.")
    void allocatePickingTote4() {
        final Outbound outbound = anOutbound().build();
        final Location tote = aLocation()
                .inventories(anInventory())
                .build();

        assertThatThrownBy(() -> outbound.allocatePickingTote(tote))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할당하려는 토트에 상품이 존재합니다.");
    }

    @Test
    @DisplayName("출고에 전달한 토트가 이미 할당된 예외가 발생한다.")
    void allocatePickingTote5() {
        final Outbound outbound = anOutbound()
                .pickingTote(aLocation())
                .build();
        final Location tote = aLocation().build();

        assertThatThrownBy(() -> outbound.allocatePickingTote(tote))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 출고에 토트가 할당되어 있습니다.");
    }

    @Test
    @DisplayName("포장재가 할당되어 있지 않으면 예외가 발생한다.")
    void allocatePickingTote6() {
        final Outbound outbound = anOutbound()
                .pickingTote(null)
                .packagingMaterial(null)
                .build();
        final Location tote = aLocation().build();

        assertThatThrownBy(() -> outbound.allocatePickingTote(tote))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("포장재가 할당되어 있지 않습니다.");
    }

    @Test
    @DisplayName("출고 상품을 집품할 집품 목록을 할당한다.")
    void allocatePicking() {
        // given
        final Outbound outbound = anOutbound().build();
        final Inventories inventories = anInventories().build();

        // when
        outbound.allocatePicking(inventories);

        // then
        assertThat(outbound.getOutboundProducts().toList().get(0).getPickings()).isNotNull();
    }
}
