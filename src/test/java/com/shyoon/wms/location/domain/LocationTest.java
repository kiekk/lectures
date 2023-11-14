package com.shyoon.wms.location.domain;

import com.shyoon.wms.inbound.domain.LPN;
import com.shyoon.wms.inbound.domain.LPNFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    @DisplayName("로케이션에 LPN을 할당한다.")
    void assignLPN() {
        final Location location = LocationFixture.aLocation().build();
        final LPN lpn = LPNFixture.anLPN().build();

        location.assignLPN(lpn);
    }

}
