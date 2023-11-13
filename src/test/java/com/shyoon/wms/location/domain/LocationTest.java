package com.shyoon.wms.location.domain;

import com.shyoon.wms.inbound.domain.InboundItemFixture;
import com.shyoon.wms.inbound.domain.LPN;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class LocationTest {

    @Test
    @DisplayName("로케이션에 LPN을 할당한다.")
    void assignLPN() {
        final String locationBarcode = "A-1-1";
        final StorageType storageType = StorageType.TOTE;
        final UsagePurpose usagePurpose = UsagePurpose.MOVE;
        final Location location = new Location(
                locationBarcode,
                storageType,
                usagePurpose
        );

        final String lpnBarcode = "LPN-1";
        final LocalDateTime expirationAt = LocalDateTime.now().plusDays(1);
        final LPN lpn = new LPN(
                lpnBarcode,
                expirationAt,
                InboundItemFixture.anInboundItem().build()
        );
        location.assignLPN(lpn);
    }
}
