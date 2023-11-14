package com.shyoon.wms.location.domain;

import com.shyoon.wms.inbound.domain.LPN;
import com.shyoon.wms.inbound.domain.LPNFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocationTest {

    @Test
    @DisplayName("로케이션에 LPN을 할당한다.")
    void assignLPN() {
        final Location location = LocationFixture.aLocation().build();
        final LPN lpn = LPNFixture.anLPN().build();

        location.assignLPN(lpn);

        assertThat(location.getLocationLPNList()).hasSize(1);
        assertThat(location.getLocationLPNList().get(0).getInventoryQuantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("로케이션에 LPN을 할당한다. 이미 LPN이 존재하면 생성하지 않고 재고만 증가시킨다.")
    void already_exists_assignLPN() {
        final Location location = LocationFixture.aLocation().build();
        final LPN lpn = LPNFixture.anLPN().build();

        location.assignLPN(lpn);
        location.assignLPN(lpn); // 로케이션을 생성하지 않고 재고 1 증가

        assertThat(location.getLocationLPNList()).hasSize(1);
        assertThat(location.getLocationLPNList().get(0).getInventoryQuantity()).isEqualTo(2);
    }

}
